package com.harbourline.booking.service;

import org.springframework.stereotype.Component;

/** Customs declarations are needed when goods cross the GB / EU border (either direction). */
@Component
public class CrossBorderPolicy {
    public boolean needsCustoms(String originCountry, String destinationCountry) {
        boolean fromGb = "GB".equals(originCountry);
        boolean toGb = "GB".equals(destinationCountry);
        return fromGb != toGb;
    }
}
