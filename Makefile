.PHONY: up down test

up:
	docker compose up -d --build

down:
	docker compose down

test:
	cd services/booking-service && ./mvnw -q test
	cd services/tracking-worker && make test
	cd services/customs-docs && npm test
