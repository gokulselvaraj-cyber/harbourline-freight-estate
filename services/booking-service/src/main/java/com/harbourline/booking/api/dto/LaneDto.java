package com.harbourline.booking.api.dto;

import com.harbourline.booking.domain.Lane;

public record LaneDto(String origin, String destination, String mode, String depot) {
    public static LaneDto of(Lane l) {
        return new LaneDto(l.getOrigin(), l.getDestination(), l.getMode().name(), l.getDepotCode());
    }
}
