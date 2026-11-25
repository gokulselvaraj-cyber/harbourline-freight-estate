"""North Sea Feeder Co. - webhook (vessel milestones)."""
from .base import CarrierAdapter


class NorthseaFeederAdapter(CarrierAdapter):
    code = "NSF"
    status_map = {
        "VD": "DEP",
        "VA": "ARR",
        "VX": "DLY",
        "CR": "POD",
    }

    def status_field(self):
        return "code"
