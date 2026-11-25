import json
from pathlib import Path

from tracking_worker.carriers.northsea_feeder import NorthseaFeederAdapter


def test_fixture_maps_to_dly():
    raw = json.loads((Path(__file__).parent / "fixtures" / "northsea_feeder.json").read_text())
    assert raw["code"] == "VX"
    assert NorthseaFeederAdapter().normalise(raw).code == "DLY"
