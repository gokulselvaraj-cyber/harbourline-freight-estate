from datetime import datetime

from ..models import CarrierEvent

NORMALISED = {"DEP", "ARR", "DLY", "POD", "BRD", "EXC"}


class CarrierAdapter:
    code = "XXX"
    status_map: dict[str, str] = {}

    def normalise(self, raw: dict) -> CarrierEvent | None:
        code = self.status_map.get(str(raw.get(self.status_field())))
        if code is None:
            return None
        return CarrierEvent(
            booking_ref=self.booking_ref(raw),
            carrier=self.code,
            code=code,
            at=self.timestamp(raw),
            delay_hours=int(raw.get("delay_h", 0) or 0),
            location=raw.get("loc"),
        )

    def status_field(self) -> str:
        return "status"

    def booking_ref(self, raw: dict) -> str:
        return raw["reference"]

    def timestamp(self, raw: dict) -> datetime:
        return datetime.fromisoformat(raw["timestamp"])
