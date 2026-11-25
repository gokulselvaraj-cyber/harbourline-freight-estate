import json
from pathlib import Path

from tracking_worker.carriers.iberline import IberlineAdapter


def test_fixture_maps_to_brd():
    raw = json.loads((Path(__file__).parent / "fixtures" / "iberline.json").read_text())
    assert raw["estado"] == "ADUANA"
    assert IberlineAdapter().normalise(raw).code == "BRD"
