"""Harbourline own trucks - telematics webhook."""
from .base import CarrierAdapter


class OwnFleetAdapter(CarrierAdapter):
    code = "HFF"
    status_map = {
        "trip_start": "DEP",
        "geofence_in": "ARR",
        "late": "DLY",
        "pod_signed": "POD",
        "border": "BRD",
    }

    def status_field(self):
        return "type"
