import json
from pathlib import Path

from tracking_worker.carriers.own_fleet import OwnFleetAdapter


def test_fixture_maps_to_pod():
    raw = json.loads((Path(__file__).parent / "fixtures" / "own_fleet.json").read_text())
    assert raw["type"] == "pod_signed"
    assert OwnFleetAdapter().normalise(raw).code == "POD"
