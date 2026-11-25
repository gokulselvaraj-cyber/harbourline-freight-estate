# booking-service

Takes shipment bookings from the customer portal and the EDI gateway, prices them against the
2026 lane tariff, asks customs-docs for declarations on GB<->EU moves, books a depot dispatch slot
and tells tracking-worker about it over RabbitMQ.

| | |
|---|---|
| Owner | Booking squad (Maren Okafor) |
| Port | 8080 (`SERVER_PORT`) |
| DB | Postgres `booking` (Flyway, `src/main/resources/db/migration`) |
| Publishes | `hf.bookings` / `booking.confirmed.v1`, `booking.dispatched.v1` |
| Calls | customs-docs `POST /v1/declarations` |

## Run locally
```
docker compose up -d postgres rabbitmq
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## Layers
`api` (controllers, DTOs) -> `service` -> `repository` (Spring Data JPA) -> Postgres.
`domain` holds the JPA entities. `legacy/` is not wired into the app.

## Rating
See `RatingService`. Fuel surcharge comes from `harbourline.rating.fuel-surcharge-pct` (per profile).
Road groupage is charged on chargeable weight (1 m3 = 333 kg).
