import json
from pathlib import Path

from tracking_worker.carriers.baltic_roro import BalticRoroAdapter


def test_fixture_maps_to_dep():
    raw = json.loads((Path(__file__).parent / "fixtures" / "baltic_roro.json").read_text())
    assert raw["event"] == "SAILED"
    assert BalticRoroAdapter().normalise(raw).code == "DEP"
