"""One-off: recompute ETAs for bookings dispatched 1-14 Oct 2026 after the Celtic Ferries outage.
Run once by T. Wierzba on 2026-10-16. Kept for the audit trail; do not run again.
"""
import os

import psycopg

DSN = os.environ["TRACKING_DB_DSN"]

with psycopg.connect(DSN) as conn:
    conn.execute("""
        UPDATE shipment_eta e
           SET eta = e.eta + interval '36 hours', updated_at = now()
          FROM shipment s
         WHERE s.booking_ref = e.booking_ref
           AND s.slot_date BETWEEN '2026-10-01' AND '2026-10-14'
           AND s.destination IN ('IEDUB', 'IEORK')
    """)
