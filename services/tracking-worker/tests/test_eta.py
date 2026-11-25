from datetime import date, datetime

from tracking_worker.eta import compute_eta, needs_notification
from tracking_worker.models import CarrierEvent, DispatchedBooking, Receiver


def _b(dest="NLRTM", depot="FXT", days=2):
    return DispatchedBooking("HLF-26-TEST01", depot, date(2026, 11, 2), 3, dest, days, Receiver("TEST-CONSIGNEE-0001", None))


def test_sea_adds_dwell(settings):
    assert compute_eta(_b(), "SEA", [], settings) == datetime(2026, 11, 4, 18, 0)


def test_road_skips_weekend(settings):
    b = _b(dest="DEHAM", depot="RTM", days=5)
    assert compute_eta(b, "ROAD", [], settings).date() == date(2026, 11, 9)


def test_delay_event_moves_eta(settings):
    ev = CarrierEvent("HLF-26-TEST01", "CSS", "DLY", datetime(2026, 11, 3), delay_hours=6)
    assert compute_eta(_b(), "SEA", [ev], settings) == datetime(2026, 11, 5, 0, 0)


def test_notification_threshold():
    a = datetime(2026, 11, 4, 10)
    assert not needs_notification(a, datetime(2026, 11, 4, 11), 2)
    assert needs_notification(a, datetime(2026, 11, 4, 13), 2)
