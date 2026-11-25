import json
from pathlib import Path

from tracking_worker.carriers.channel_shortsea import ChannelShortseaAdapter


def test_fixture_maps_to_arr():
    raw = json.loads((Path(__file__).parent / "fixtures" / "channel_shortsea.json").read_text())
    assert raw["milestone"] == "ATA"
    assert ChannelShortseaAdapter().normalise(raw).code == "ARR"
