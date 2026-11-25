CREATE TABLE shipper (
    shipper_id   BIGSERIAL PRIMARY KEY,
    account_no   VARCHAR(12) NOT NULL UNIQUE,
    company_name VARCHAR(120) NOT NULL,
    eori         VARCHAR(17)
);

-- delivery_party = the consignee / receiver of the goods (personal data when a private person)
CREATE TABLE delivery_party (
    delivery_party_id BIGSERIAL PRIMARY KEY,
    contact_name   VARCHAR(120) NOT NULL,
    company_name   VARCHAR(120),
    phone          VARCHAR(32),
    email          VARCHAR(160),
    address_line1  VARCHAR(160) NOT NULL,
    city           VARCHAR(80) NOT NULL,
    postcode       VARCHAR(12) NOT NULL,
    country        CHAR(2) NOT NULL,
    eori           VARCHAR(17),
    created_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    delivered_at   TIMESTAMPTZ
);

CREATE TABLE booking (
    booking_id        BIGSERIAL PRIMARY KEY,
    booking_ref       VARCHAR(14) NOT NULL UNIQUE,
    shipper_id        BIGINT NOT NULL REFERENCES shipper(shipper_id),
    delivery_party_id BIGINT NOT NULL REFERENCES delivery_party(delivery_party_id),
    lane_id           BIGINT NOT NULL REFERENCES lane(lane_id),
    equipment         VARCHAR(6) NOT NULL,
    status            VARCHAR(16) NOT NULL,
    quoted_total      NUMERIC(12,2),
    currency          CHAR(3) NOT NULL DEFAULT 'GBP',
    customs_ref       VARCHAR(24),
    ready_date        DATE NOT NULL,
    created_at        TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE cargo_item (
    cargo_item_id BIGSERIAL PRIMARY KEY,
    booking_id    BIGINT NOT NULL REFERENCES booking(booking_id) ON DELETE CASCADE,
    description   VARCHAR(200) NOT NULL,
    hs_code       VARCHAR(10),
    packages      INT NOT NULL,
    gross_kg      NUMERIC(10,2) NOT NULL,
    volume_m3     NUMERIC(8,3) NOT NULL,
    dangerous     BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX ix_booking_status ON booking(status);
