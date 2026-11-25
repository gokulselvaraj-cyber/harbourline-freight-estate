# ADR 0003: Lodge customs declarations through a broker API

Status: Accepted (2024-09)

## Decision
We do not connect to HMRC CDS or EU systems directly. customs-docs builds the declaration and submits it to the ClearPath Broker API, which lodges it.
