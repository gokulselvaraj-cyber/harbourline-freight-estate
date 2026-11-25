package com.harbourline.booking.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "harbourline.events")
public record EventProperties(String exchange, String confirmedKey, String dispatchedKey) {
}
