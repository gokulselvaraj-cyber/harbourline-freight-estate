# Data retention

| Data | Where | Keep |
|---|---|---|
| Receiver contact details (name, phone, email, address) | booking `delivery_party`, tracking `shipment` | 24 months after delivery, then redacted |
| Bookings and invoices | booking DB | 7 years (tax) |
| Customs declarations | broker + customs-docs | 4 years (customs audit) |
| Tracking events | tracking DB | 18 months |
| Database backups | backup bucket | 35 days |

The redaction runs `purge_delivery_party()` in the booking database (nightly cron on the db server).
