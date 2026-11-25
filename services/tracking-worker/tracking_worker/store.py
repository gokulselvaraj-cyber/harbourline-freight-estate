"""Postgres access for the tracking database."""
import psycopg

from .models import CarrierEvent


class Store:
    def __init__(self, dsn: str):
        self._conn = psycopg.connect(dsn, autocommit=True)

    def save_dispatch(self, b, mode: str):
        self._conn.execute(
            "INSERT INTO shipment (booking_ref, depot_code, slot_date, destination, mode, transit_days, "
            "receiver_name, receiver_phone) VALUES (%s,%s,%s,%s,%s,%s,%s,%s) ON CONFLICT (booking_ref) DO NOTHING",
            (b.booking_ref, b.depot_code, b.slot_date, b.destination, mode, b.transit_days,
             b.receiver.name, b.receiver.phone))

    def save_event(self, e: CarrierEvent):
        self._conn.execute(
            "INSERT INTO tracking_event (booking_ref, carrier, code, event_at, delay_hours, location) "
            "VALUES (%s,%s,%s,%s,%s,%s)",
            (e.booking_ref, e.carrier, e.code, e.at, e.delay_hours, e.location))

    def events_for(self, ref: str) -> list[CarrierEvent]:
        rows = self._conn.execute(
            "SELECT booking_ref, carrier, code, event_at, delay_hours, location FROM tracking_event "
            "WHERE booking_ref = %s ORDER BY event_at", (ref,)).fetchall()
        return [CarrierEvent(*r) for r in rows]

    def current_eta(self, ref: str):
        row = self._conn.execute("SELECT eta FROM shipment_eta WHERE booking_ref = %s", (ref,)).fetchone()
        return row[0] if row else None

    def upsert_eta(self, ref: str, eta):
        self._conn.execute(
            "INSERT INTO shipment_eta (booking_ref, eta, updated_at) VALUES (%s,%s,now()) "
            "ON CONFLICT (booking_ref) DO UPDATE SET eta = EXCLUDED.eta, updated_at = now()", (ref, eta))

    def shipment(self, ref: str):
        return self._conn.execute(
            "SELECT booking_ref, depot_code, slot_date, destination, mode, transit_days, receiver_name, "
            "receiver_phone FROM shipment WHERE booking_ref = %s", (ref,)).fetchone()
