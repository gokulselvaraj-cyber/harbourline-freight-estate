# tracking-worker

Keeps the estimated time of arrival (ETA) for every dispatched booking and texts the receiver when it
changes by more than two hours.

- Consumes `tracking.booking-dispatched` (routing key `booking.dispatched.v1` on `hf.bookings`) and
  `tracking.carrier-events` (`carrier.event.v1` on `hf.carrier`, fed by the carrier adapters).
- Writes `tracking_event` and `shipment_eta` in the `tracking` database (`sql/schema.sql`).
- Publishes `tracking.eta-updated.v1` on `hf.tracking`.
- SMS via NotifyHub (`NOTIFYHUB_URL`).

Owner: Visibility team (Tomasz Wierzba). Metrics on :9102.

Run: `make install test run`. Config per env in `config/<env>.yaml`, secrets from env vars only.
