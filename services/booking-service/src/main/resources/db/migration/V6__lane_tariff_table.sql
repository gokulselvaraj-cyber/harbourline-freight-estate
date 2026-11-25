CREATE TABLE lane_tariff (
    origin       CHAR(5) NOT NULL,
    destination  CHAR(5) NOT NULL,
    mode         VARCHAR(4) NOT NULL,
    equipment    VARCHAR(6) NOT NULL,
    weight_band  SMALLINT NOT NULL,
    rate_gbp     NUMERIC(10,2) NOT NULL,
    transit_days SMALLINT NOT NULL,
    valid_from   DATE NOT NULL,
    PRIMARY KEY (origin, destination, mode, equipment, weight_band, valid_from)
);
COMMENT ON COLUMN lane_tariff.rate_gbp IS
  'FCL/FTL: per unit. GRP (road groupage): per 100 kg chargeable. LCL: per freight tonne (W/M).';
