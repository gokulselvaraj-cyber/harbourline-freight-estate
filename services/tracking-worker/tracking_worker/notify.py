"""Sends ETA text messages to the receiver through NotifyHub (external SaaS, EU region)."""
import logging

import httpx

log = logging.getLogger(__name__)


def send_eta_sms(settings, booking_ref: str, receiver_name: str, phone: str | None, eta) -> bool:
    if not phone:
        return False
    body = {
        "to": phone,
        "template": "hf-eta-update",
        "vars": {"name": receiver_name, "ref": booking_ref, "eta": eta.strftime("%a %d %b %H:%M")},
    }
    r = httpx.post(settings.notifyhub_url, json=body, timeout=5.0,
                   headers={"Authorization": f"Bearer {settings.notifyhub_token}"})
    if r.status_code >= 300:
        log.warning("sms failed for %s: %s", booking_ref, r.status_code)
        return False
    return True
