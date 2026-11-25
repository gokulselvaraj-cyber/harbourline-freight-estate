from prometheus_client import Counter, Histogram, start_http_server

EVENTS = Counter("hf_tracking_events_total", "Carrier events processed", ["carrier", "code"])
ETA_SHIFT = Histogram("hf_tracking_eta_shift_hours", "ETA change per recompute",
                      buckets=(0.5, 1, 2, 4, 8, 24, 48))
SMS = Counter("hf_tracking_sms_total", "Receiver SMS sent", ["result"])


def serve(port: int):
    start_http_server(port)
