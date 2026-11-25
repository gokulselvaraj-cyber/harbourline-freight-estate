# ADR 0004: Suggest HS codes with an AI model

Status: Accepted (2025-05)

## Decision
Shippers often leave the HS code empty. customs-docs asks the AI gateway for a code and only accepts it above a confidence threshold (0.85 in production); otherwise the trade desk classifies by hand.
