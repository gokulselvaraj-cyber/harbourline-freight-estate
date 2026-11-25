"""Iberline Carga (Bilbao, Valencia) - REST poll."""
from .base import CarrierAdapter


class IberlineAdapter(CarrierAdapter):
    code = "IBL"
    status_map = {
        "SALIDA": "DEP",
        "LLEGADA": "ARR",
        "RETRASO": "DLY",
        "ENTREGADO": "POD",
        "ADUANA": "BRD",
        "INCIDENCIA": "EXC",
    }

    def status_field(self):
        return "estado"
