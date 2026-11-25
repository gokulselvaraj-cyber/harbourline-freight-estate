ALTER TABLE booking ADD COLUMN customs_status VARCHAR(16);
ALTER TABLE booking ADD COLUMN insurance_requested BOOLEAN NOT NULL DEFAULT FALSE;
CREATE INDEX ix_booking_customs_ref ON booking(customs_ref);
