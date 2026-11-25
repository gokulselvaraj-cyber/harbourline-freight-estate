"""Render and send a ZPL label for a booking to the depot printer."""
import argparse
import os
import socket
from pathlib import Path

import requests

TEMPLATE = (Path(__file__).parent / "templates" / "pallet.zpl").read_text()


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("booking_ref")
    ap.add_argument("--copies", type=int, default=1)
    a = ap.parse_args()
    api = os.environ.get("BOOKING_API_URL", "http://booking.hf.internal:8080")
    b = requests.get(f"{api}/v1/bookings/{a.booking_ref}", timeout=5).json()
    zpl = TEMPLATE.replace("{ref}", b["bookingRef"]).replace("{status}", b["status"])
    printer = os.environ.get("LABEL_PRINTER", "localhost")
    with socket.create_connection((printer, 9100), timeout=5) as s:
        for _ in range(a.copies):
            s.sendall(zpl.encode())


if __name__ == "__main__":
    main()
