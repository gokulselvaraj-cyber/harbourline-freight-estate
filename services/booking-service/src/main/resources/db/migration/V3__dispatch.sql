CREATE TABLE dispatch_slot (
    slot_id      BIGSERIAL PRIMARY KEY,
    depot_code   VARCHAR(3) NOT NULL REFERENCES depot(depot_code),
    slot_date    DATE NOT NULL,
    dock_no      SMALLINT NOT NULL,
    booking_id   BIGINT REFERENCES booking(booking_id),
    UNIQUE (depot_code, slot_date, dock_no)
);

CREATE TABLE manifest (
    manifest_id   BIGSERIAL PRIMARY KEY,
    manifest_no   VARCHAR(16) NOT NULL UNIQUE,
    depot_code    VARCHAR(3) NOT NULL REFERENCES depot(depot_code),
    vehicle_ref   VARCHAR(20) NOT NULL,
    sailing_date  DATE NOT NULL,
    closed        BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE manifest_line (
    manifest_id BIGINT NOT NULL REFERENCES manifest(manifest_id),
    booking_id  BIGINT NOT NULL REFERENCES booking(booking_id),
    PRIMARY KEY (manifest_id, booking_id)
);
