# ADR 0006: Lane tariff shipped as a migration

Status: Accepted (2025-12)

## Decision
The pricing workbook is exported to `V7__lane_tariff_2026.sql`. Later revisions are new rows with a later `valid_from`, never updates.
