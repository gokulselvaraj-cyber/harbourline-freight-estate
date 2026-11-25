import pytest


class _S:
    sea_dwell_hours = 12
    road_border_buffer_hours = 4
    notify_threshold_hours = 2


@pytest.fixture
def settings():
    return _S()
