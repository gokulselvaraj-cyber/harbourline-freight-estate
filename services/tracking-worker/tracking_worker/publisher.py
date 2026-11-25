"""Publishes tracking.eta-updated.v1 on exchange hf.tracking."""
import json

import pika

EXCHANGE = "hf.tracking"
ROUTING_KEY = "tracking.eta-updated.v1"


def publish_eta(channel, booking_ref: str, eta, source: str):
    channel.basic_publish(
        exchange=EXCHANGE,
        routing_key=ROUTING_KEY,
        body=json.dumps({"bookingRef": booking_ref, "eta": eta.isoformat(), "source": source}),
        properties=pika.BasicProperties(content_type="application/json", delivery_mode=2),
    )
