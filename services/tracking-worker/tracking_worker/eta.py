"""ETA rules.

eta = slot date 06:00 at the depot + transit_days
      + sea port dwell (sea lanes) or border buffer (road lanes crossing GB/EU)
      + reported carrier delays.
Weekends do not count for road transit; sea transit runs 7 days a week.
"""
from datetime import datetime, time, timedelta

from .models import CarrierEvent, DispatchedBooking

SEA_PORTS = {"NLRTM", "NLAMS", "BEANR", "BEZEE", "DEHAM", "DEBRV", "PLGDY", "PLGDN", "GBFXT", "GBIMM",
             "GBTEE", "GBSOU", "GBTIL", "FRLEH", "FRDKK", "IEDUB", "IEORK", "ESBIO", "ESVLC", "PTLEI",
             "ITGOA", "DKAAR", "SEGOT", "FIHEL", "LTKLJ", "NOOSL", "NOBGO"}


def _add_working_days(start: datetime, days: int) -> datetime:
    d = start
    while days > 0:
        d += timedelta(days=1)
        if d.weekday() < 5:
            days -= 1
    return d


def compute_eta(b: DispatchedBooking, mode: str, events: list[CarrierEvent], settings) -> datetime:
    start = datetime.combine(b.slot_date, time(6, 0))
    if mode == "SEA":
        eta = start + timedelta(days=b.transit_days, hours=settings.sea_dwell_hours)
    else:
        eta = _add_working_days(start, b.transit_days)
        crosses = (b.depot_code in {"FXT", "IMM", "TSB"}) != b.destination.startswith("GB")
        if crosses:
            eta += timedelta(hours=settings.road_border_buffer_hours)
    for e in events:
        if e.code == "DLY":
            eta += timedelta(hours=e.delay_hours)
        if e.code == "POD":
            return e.at
    return eta


def needs_notification(old: datetime | None, new: datetime, threshold_hours: int) -> bool:
    if old is None:
        return True
    return abs((new - old).total_seconds()) >= threshold_hours * 3600
