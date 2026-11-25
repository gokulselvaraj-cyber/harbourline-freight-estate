package com.harbourline.booking.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "harbourline.customs-docs")
public record CustomsDocsProperties(String baseUrl, Duration connectTimeout, Duration readTimeout,
                                    int maxRetries, Duration retryBackoff) {
}
