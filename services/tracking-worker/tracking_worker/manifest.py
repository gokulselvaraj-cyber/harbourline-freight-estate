"""Adapter manifest: which carrier adapters are loaded, and which inbound feed each one reads.

Not to be confused with a cargo manifest - this is the plugin registry of the worker.
"""
import importlib
from dataclasses import dataclass


@dataclass(frozen=True)
class AdapterEntry:
    code: str
    module: str
    feed: str       # webhook | sftp | poll
    enabled: bool = True


class Manifest:
    ENTRIES = [
        AdapterEntry("VLN", "tracking_worker.carriers.vrachtlijn", "webhook"),
        AdapterEntry("NKR", "tracking_worker.carriers.nordkurier", "poll"),
        AdapterEntry("BRR", "tracking_worker.carriers.baltic_roro", "sftp"),
        AdapterEntry("CSS", "tracking_worker.carriers.channel_shortsea", "webhook"),
        AdapterEntry("IBL", "tracking_worker.carriers.iberline", "poll"),
        AdapterEntry("NSF", "tracking_worker.carriers.northsea_feeder", "webhook"),
        AdapterEntry("CFY", "tracking_worker.carriers.celtic_ferries", "poll"),
        AdapterEntry("HFF", "tracking_worker.carriers.own_fleet", "webhook"),
        AdapterEntry("PTX", "tracking_worker.carriers.polartrans", "poll", enabled=False),
    ]

    def load(self):
        return {e.code: importlib.import_module(e.module) for e in self.ENTRIES if e.enabled}
