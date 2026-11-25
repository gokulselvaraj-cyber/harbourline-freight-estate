"""Loads config/<HF_ENV>.yaml and overlays environment variables."""
import os
from dataclasses import dataclass
from pathlib import Path

import yaml


@dataclass(frozen=True)
class Settings:
    env: str
    db_dsn: str
    rabbit_url: str
    notifyhub_url: str
    notifyhub_token: str
    metrics_port: int
    sea_dwell_hours: int
    road_border_buffer_hours: int
    notify_threshold_hours: int
    concurrency: int


def load() -> Settings:
    env = os.environ.get("HF_ENV", "dev")
    raw = yaml.safe_load((Path(__file__).parent.parent / "config" / f"{env}.yaml").read_text())
    db = raw["db"]
    dsn = os.environ.get("TRACKING_DB_DSN") or (
        f"postgresql://{db['user']}:{os.environ.get('TRACKING_DB_PASSWORD', '')}@{db['host']}:5432/{db['name']}")
    return Settings(
        env=env,
        db_dsn=dsn,
        rabbit_url=os.environ.get("RABBIT_URL", f"amqp://guest:guest@{raw['rabbit']['host']}:5672/%2Fhf"),
        notifyhub_url=os.environ.get("NOTIFYHUB_URL", "https://sandbox.notifyhub.example/v2/sms"),
        notifyhub_token=os.environ.get("NOTIFYHUB_TOKEN", ""),
        metrics_port=int(os.environ.get("METRICS_PORT", "9102")),
        sea_dwell_hours=raw["eta"]["sea_port_dwell_hours"],
        road_border_buffer_hours=raw["eta"]["road_border_buffer_hours"],
        notify_threshold_hours=raw["eta"]["notify_threshold_hours"],
        concurrency=int(os.environ.get("CONCURRENCY", raw["concurrency"])),
    )
