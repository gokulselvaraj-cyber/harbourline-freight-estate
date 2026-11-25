"""Polartrans (Norway) - not live yet, disabled in the manifest."""
from .base import CarrierAdapter


class PolartransAdapter(CarrierAdapter):
    code = "PTX"
    status_map = {
        "AVG": "DEP",
        "ANK": "ARR",
        "FORS": "DLY",
        "LEV": "POD",
    }

    def status_field(self):
        return "status"
