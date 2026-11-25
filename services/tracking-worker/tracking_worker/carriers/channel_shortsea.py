"""Channel Shortsea (Felixstowe-Rotterdam feeder) - webhook."""
from .base import CarrierAdapter


class ChannelShortseaAdapter(CarrierAdapter):
    code = "CSS"
    status_map = {
        "ATD": "DEP",
        "ATA": "ARR",
        "ETA_CHANGE": "DLY",
        "GATE_OUT": "POD",
    }

    def status_field(self):
        return "milestone"
