"""Baltic Ro-Ro Lines (Gdynia, Klaipeda) - SFTP CSV drop every 15 min."""
from .base import CarrierAdapter


class BalticRoroAdapter(CarrierAdapter):
    code = "BRR"
    status_map = {
        "SAILED": "DEP",
        "DISCHARGED": "ARR",
        "DELAY": "DLY",
        "DELIVERED": "POD",
        "CUSTOMS_HOLD": "BRD",
    }

    def status_field(self):
        return "event"
