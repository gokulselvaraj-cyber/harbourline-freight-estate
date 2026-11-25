package com.harbourline.booking.domain;

/** Equipment codes used in lane_tariff.equipment. */
public enum Equipment {
    GP20("20GP", true), GP40("40GP", true), HC40("40HC", true), LCL("LCL", false),
    TRL136("TRL136", true), GRP("GRP", false);

    private final String code;
    private final boolean perUnit;

    Equipment(String code, boolean perUnit) { this.code = code; this.perUnit = perUnit; }

    public String code() { return code; }
    public boolean perUnit() { return perUnit; }

    public static Equipment fromCode(String c) {
        for (Equipment e : values()) if (e.code.equals(c)) return e;
        throw new IllegalArgumentException("Unknown equipment " + c);
    }
}
