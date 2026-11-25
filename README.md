# Harbourline Freight - platform

Sea and road freight forwarding between the UK and the EU. This repository holds the booking platform,
the tracking worker, the customs documents service, the server automation and the operational docs.

| Path | What |
|---|---|
| `services/booking-service/` | Java 17 / Spring Boot: bookings, rating, dispatch slots |
| `services/tracking-worker/` | Python: carrier events, ETA, receiver SMS |
| `services/customs-docs/` | Node/TypeScript: GB<->EU customs declarations |
| `contracts/` | OpenAPI and AsyncAPI contracts |
| `infra/ansible/` | Server and depot automation |
| `tools/depot-label-printer/` | Label printing at the depots |
| `docs/` | Architecture, ADRs, runbooks, policies, customer help |
| `ops/` | Alerts, SLOs, cron, sample logs |
| `data/reference/` | Depots, carriers, ports reference data |

Journey: booking -> rating -> customs docs (GB<->EU only) -> dispatch -> tracking events / ETA.
See `docs/architecture.md` and `docs/diagrams/booking-journey.mmd`.

Local stack: `docker compose up`.
