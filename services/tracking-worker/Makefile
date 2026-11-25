.PHONY: install test lint run

install:
	python3.11 -m venv .venv && .venv/bin/pip install -r requirements.txt -e .

test:
	.venv/bin/pytest -q

lint:
	.venv/bin/ruff check tracking_worker tests

run:
	HF_ENV=dev .venv/bin/hf-tracking-worker
