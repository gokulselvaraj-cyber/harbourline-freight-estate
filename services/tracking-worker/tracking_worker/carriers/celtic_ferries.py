"""Celtic Ferries (Holyhead/Dublin unaccompanied trailers) - poll."""
from .base import CarrierAdapter


class CelticFerriesAdapter(CarrierAdapter):
    code = "CFY"
    status_map = {
        "BOARDED": "DEP",
        "LANDED": "ARR",
        "CANCELLED_SAILING": "DLY",
        "COLLECTED": "POD",
        "REVENUE_CHECK": "BRD",
    }

    def status_field(self):
        return "status"
