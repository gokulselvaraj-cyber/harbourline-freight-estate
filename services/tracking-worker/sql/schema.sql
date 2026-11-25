-- tracking database (owned by tracking-worker)
CREATE TABLE shipment (
    booking_ref    VARCHAR(14) PRIMARY KEY,
    depot_code     VARCHAR(3) NOT NULL,
    slot_date      DATE NOT NULL,
    destination    CHAR(5) NOT NULL,
    mode           VARCHAR(4) NOT NULL,
    transit_days   SMALLINT NOT NULL,
    receiver_name  VARCHAR(120),     -- copied from booking delivery_party.contact_name
    receiver_phone VARCHAR(32),      -- copied from booking delivery_party.phone
    created_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE tracking_event (
    event_id    BIGSERIAL PRIMARY KEY,
    booking_ref VARCHAR(14) NOT NULL REFERENCES shipment(booking_ref),
    carrier     VARCHAR(3) NOT NULL,
    code        VARCHAR(3) NOT NULL,
    event_at    TIMESTAMPTZ NOT NULL,
    delay_hours SMALLINT NOT NULL DEFAULT 0,
    location    VARCHAR(40)
);
CREATE INDEX ix_tracking_event_ref ON tracking_event(booking_ref, event_at);

CREATE TABLE shipment_eta (
    booking_ref VARCHAR(14) PRIMARY KEY REFERENCES shipment(booking_ref),
    eta         TIMESTAMPTZ NOT NULL,
    updated_at  TIMESTAMPTZ NOT NULL
);
