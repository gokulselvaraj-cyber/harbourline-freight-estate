"""Nordkurier Spedition (Hamburg) - polling adapter."""
from datetime import datetime

from .base import CarrierAdapter


class NordkurierAdapter(CarrierAdapter):
    code = "NKR"
    # Statuscodes laut Schnittstellenbeschreibung v4.2 (Abschnitt 7).
    status_map = {
        "10": "DEP",   # Abgefahren
        "20": "ARR",   # Angekommen im Umschlaglager
        "30": "DLY",   # Verspätung gemeldet
        "40": "BRD",   # Zollabfertigung
        "90": "POD",   # Zugestellt
        "99": "EXC",   # Störung
    }

    def status_field(self):
        return "sts"

    def booking_ref(self, raw):
        # Referenz kommt manchmal mit Präfix "HF/", das schneiden wir ab.
        return raw["ref"].removeprefix("HF/")

    def timestamp(self, raw):
        return datetime.fromisoformat(raw["zeit"])
