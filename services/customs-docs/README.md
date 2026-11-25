# customs-docs

Creates customs declarations for bookings that cross the GB/EU border and hands them to our customs
broker (ClearPath Broker API). Also renders the commercial invoice, packing list and T1 transit
documents from `templates/`.

- `POST /v1/declarations` (called by booking-service), `GET /v1/declarations/:ref`, `GET /health`
- Consumes `customs.booking-confirmed` to pre-fill drafts.
- HS codes: if the shipper did not give one, `HsClassifier` asks the AI gateway (prompt in `prompts/`).
- Country rules in `src/rules/countries/`.
- Listens on `PORT` (default 3000).

Contract: `contracts/customs-docs.openapi.yaml`. Booking API client in `src/generated/` is generated
(`npm run gen:client`), do not edit it by hand. `vendor/tariff-codes-lite` is a vendored copy.
