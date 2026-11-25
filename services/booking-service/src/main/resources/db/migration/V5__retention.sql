-- Retention: receiver contact details are kept 24 months after delivery (docs/policies/data-retention.md).
CREATE OR REPLACE FUNCTION purge_delivery_party() RETURNS INTEGER AS $$
DECLARE n INTEGER;
BEGIN
    UPDATE delivery_party
       SET contact_name = 'REDACTED', phone = NULL, email = NULL, address_line1 = 'REDACTED'
     WHERE delivered_at < now() - INTERVAL '24 months'
       AND contact_name <> 'REDACTED';
    GET DIAGNOSTICS n = ROW_COUNT;
    RETURN n;
END;
$$ LANGUAGE plpgsql;
