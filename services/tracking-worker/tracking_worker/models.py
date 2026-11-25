"""Event and ETA models. The receiver is the consignee of the booking (booking-service: Consignee)."""
from dataclasses import dataclass
from datetime import date, datetime


@dataclass
class Receiver:
    name: str
    phone: str | None


@dataclass
class DispatchedBooking:
    booking_ref: str
    depot_code: str
    slot_date: date
    dock_no: int
    destination: str
    transit_days: int
    receiver: Receiver

    @classmethod
    def from_event(cls, e: dict) -> "DispatchedBooking":
        return cls(
            booking_ref=e["bookingRef"],
            depot_code=e["depotCode"],
            slot_date=date.fromisoformat(e["slotDate"]),
            dock_no=int(e["dockNo"]),
            destination=e["destination"],
            transit_days=int(e["transitDays"]),
            receiver=Receiver(name=e.get("receiverName", ""), phone=e.get("receiverPhone")),
        )


@dataclass
class CarrierEvent:
    booking_ref: str
    carrier: str
    code: str            # normalised: DEP, ARR, DLY, POD, BRD (border), EXC
    at: datetime
    delay_hours: int = 0
    location: str | None = None
