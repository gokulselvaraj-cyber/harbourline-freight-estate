# ADR 0001: Use RabbitMQ instead of Kafka

Status: Accepted (2024-06)

## Decision
We ran Kafka for tracking in 2023. Volumes are small (under 50k events a day) and the team had no Kafka skills. We moved booking and tracking events to RabbitMQ topic exchanges (`hf.bookings`, `hf.carrier`, `hf.tracking`). Kafka was switched off in September 2024.
