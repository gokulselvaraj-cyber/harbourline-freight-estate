# Runbook: customs docs broker outage

Symptoms: bookings stuck in CUSTOMS_PENDING, customs-docs returns 502.
1. Check the broker status page.
2. booking-service retries twice (500 ms apart) then fails the call; the booking stays confirmed.
3. Re-submit with `POST /v1/declarations` once the broker is back.
