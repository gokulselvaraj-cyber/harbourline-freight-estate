CREATE TABLE depot (
    depot_code      VARCHAR(3) PRIMARY KEY,
    name            VARCHAR(80) NOT NULL,
    country         CHAR(2) NOT NULL,
    locode          CHAR(5) NOT NULL,
    timezone        VARCHAR(40) NOT NULL,
    dispatch_cutoff TIME NOT NULL DEFAULT '14:00'
);

CREATE TABLE lane (
    lane_id      BIGSERIAL PRIMARY KEY,
    origin       CHAR(5) NOT NULL,
    destination  CHAR(5) NOT NULL,
    mode         VARCHAR(4) NOT NULL CHECK (mode IN ('SEA','ROAD')),
    depot_code   VARCHAR(3) NOT NULL REFERENCES depot(depot_code),
    active       BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE (origin, destination, mode)
);

INSERT INTO depot VALUES
 ('FXT','Felixstowe','GB','GBFXT','Europe/London','14:00'),
 ('IMM','Immingham','GB','GBIMM','Europe/London','14:00'),
 ('TSB','Teesport','GB','GBTEE','Europe/London','14:00'),
 ('RTM','Rotterdam','NL','NLRTM','Europe/Amsterdam','15:30'),
 ('ANT','Antwerp','BE','BEANR','Europe/Brussels','15:30'),
 ('ZEE','Zeebrugge','BE','BEZEE','Europe/Brussels','15:30'),
 ('HAM','Hamburg','DE','DEHAM','Europe/Berlin','15:30'),
 ('GDY','Gdynia','PL','PLGDY','Europe/Warsaw','15:30');
