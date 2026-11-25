import json
from pathlib import Path

from tracking_worker.carriers.celtic_ferries import CelticFerriesAdapter


def test_fixture_maps_to_dly():
    raw = json.loads((Path(__file__).parent / "fixtures" / "celtic_ferries.json").read_text())
    assert raw["status"] == "CANCELLED_SAILING"
    assert CelticFerriesAdapter().normalise(raw).code == "DLY"
