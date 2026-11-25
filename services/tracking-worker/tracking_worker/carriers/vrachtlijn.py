"""Vrachtlijn NL (wegtransport Rotterdam/Antwerpen) - webhook adapter."""
from datetime import datetime

from .base import CarrierAdapter


class VrachtlijnAdapter(CarrierAdapter):
    code = "VLN"
    # Vrachtlijn stuurt Nederlandse statuscodes; we vertalen ze naar onze eigen codes.
    status_map = {
        "VERTROKKEN": "DEP",
        "AANGEKOMEN": "ARR",
        "VERTRAAGD": "DLY",
        "AFGELEVERD": "POD",
        "DOUANE": "BRD",
        "SCHADE": "EXC",
    }

    def status_field(self):
        return "statuscode"

    def booking_ref(self, raw):
        # Ons boekingsnummer staat in 'klantreferentie', niet in 'zendingnummer'.
        return raw["klantreferentie"]

    def timestamp(self, raw):
        # Tijden komen binnen als lokale tijd zonder zone (Europe/Amsterdam). Let op zomertijd!
        return datetime.strptime(raw["tijdstip"], "%d-%m-%Y %H:%M")
