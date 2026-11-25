"""RabbitMQ consumer: dispatch events from booking-service and normalised carrier events."""
import json
import logging

import pika

from . import eta as eta_rules
from .models import CarrierEvent, DispatchedBooking
from .notify import send_eta_sms
from .publisher import publish_eta

log = logging.getLogger(__name__)

DISPATCH_QUEUE = "tracking.booking-dispatched"   # hf.bookings / booking.dispatched.v1
CARRIER_QUEUE = "tracking.carrier-events"         # hf.carrier / carrier.event.v1


class Consumer:
    def __init__(self, settings, store):
        self.settings = settings
        self.store = store

    def on_dispatched(self, ch, method, props, body):
        e = json.loads(body)
        b = DispatchedBooking.from_event(e)
        mode = "SEA" if b.destination in eta_rules.SEA_PORTS and e.get("mode", "SEA") == "SEA" else "ROAD"
        self.store.save_dispatch(b, mode)
        self._recompute(ch, b, mode, source="dispatch")
        ch.basic_ack(method.delivery_tag)

    def on_carrier_event(self, ch, method, props, body):
        raw = json.loads(body)
        ev = CarrierEvent(**raw)
        self.store.save_event(ev)
        row = self.store.shipment(ev.booking_ref)
        if row:
            b = DispatchedBooking(row[0], row[1], row[2], 0, row[3], row[5], None)
            b.receiver = type("R", (), {"name": row[6], "phone": row[7]})()
            self._recompute(ch, b, row[4], source=ev.carrier)
        ch.basic_ack(method.delivery_tag)

    def _recompute(self, ch, b, mode, source):
        events = self.store.events_for(b.booking_ref)
        new = eta_rules.compute_eta(b, mode, events, self.settings)
        old = self.store.current_eta(b.booking_ref)
        self.store.upsert_eta(b.booking_ref, new)
        publish_eta(ch, b.booking_ref, new, source)
        if eta_rules.needs_notification(old, new, self.settings.notify_threshold_hours):
            send_eta_sms(self.settings, b.booking_ref, b.receiver.name, b.receiver.phone, new)

    def run(self):
        conn = pika.BlockingConnection(pika.URLParameters(self.settings.rabbit_url))
        ch = conn.channel()
        ch.basic_qos(prefetch_count=10 * self.settings.concurrency)
        ch.basic_consume(DISPATCH_QUEUE, self.on_dispatched)
        ch.basic_consume(CARRIER_QUEUE, self.on_carrier_event)
        log.info("consuming %s, %s", DISPATCH_QUEUE, CARRIER_QUEUE)
        ch.start_consuming()
