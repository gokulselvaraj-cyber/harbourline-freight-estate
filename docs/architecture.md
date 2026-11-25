# Harbourline platform architecture

_Last reviewed: March 2025 (Dev Rautela)_

## Components
- **booking-service** (Java 11, Spring Boot 3.1.4) on port **8081**. Takes bookings, prices them from the
  lane tariff, requests customs documents, books dispatch slots.
- **customs-docs** (Node) on port **8090**. Produces export/import declarations through the broker.
- **tracking-worker** (Python). Reads carrier events from the Kafka topic `shipment-events` and keeps ETAs.
- Postgres 15 (`booking`, `tracking`), behind `hf-db-01`.

## Flow
1. Portal -> nginx edge -> booking-service `POST /v1/bookings`.
2. booking-service rates the booking and, for GB<->EU moves, calls customs-docs.
3. A dispatch slot is reserved at the origin depot.
4. Dispatch and carrier events go to Kafka (`shipment-events`); tracking-worker computes the ETA and texts
   the receiver.

## Environments
dev (laptops / docker compose), test (`*.test.hf.internal`), prod (`*.hf.internal`). All servers are built by
Ansible (`infra/ansible`). UK and EU customer data is hosted in the UK.
