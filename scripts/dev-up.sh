#!/usr/bin/env bash
set -euo pipefail
: "${LOCAL_PG_PASSWORD:?set LOCAL_PG_PASSWORD first}"
docker compose up -d postgres rabbitmq
sleep 5
docker compose exec -T postgres psql -U postgres -c "CREATE DATABASE booking;" || true
docker compose exec -T postgres psql -U postgres -c "CREATE DATABASE tracking;" || true
docker compose exec -T postgres psql -U postgres -d tracking -f - < services/tracking-worker/sql/schema.sql
