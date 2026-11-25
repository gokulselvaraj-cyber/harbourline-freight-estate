import logging

from . import config, metrics
from .consumer import Consumer
from .store import Store


def run():
    logging.basicConfig(level=logging.INFO, format="%(asctime)s %(levelname)s %(name)s %(message)s")
    s = config.load()
    metrics.serve(s.metrics_port)
    Consumer(s, Store(s.db_dsn)).run()


if __name__ == "__main__":
    run()
