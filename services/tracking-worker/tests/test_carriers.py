from tracking_worker.carriers.nordkurier import NordkurierAdapter
from tracking_worker.carriers.vrachtlijn import VrachtlijnAdapter


def test_vrachtlijn_maps_dutch_codes():
    raw = {"statuscode": "AFGELEVERD", "klantreferentie": "HLF-26-TEST02", "tijdstip": "03-11-2026 14:05"}
    assert VrachtlijnAdapter().normalise(raw).code == "POD"


def test_nordkurier_strips_prefix():
    raw = {"sts": "30", "ref": "HF/HLF-26-TEST03", "zeit": "2026-11-03T08:00:00", "delay_h": 5}
    ev = NordkurierAdapter().normalise(raw)
    assert ev.booking_ref == "HLF-26-TEST03" and ev.delay_hours == 5
