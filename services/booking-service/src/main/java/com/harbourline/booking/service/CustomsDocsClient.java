package com.harbourline.booking.service;

import com.harbourline.booking.config.CustomsDocsProperties;
import com.harbourline.booking.domain.Booking;
import com.harbourline.booking.domain.CargoItem;
import com.harbourline.booking.domain.Consignee;
import com.harbourline.booking.exception.CustomsDocsUnavailableException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

/**
 * HTTP client for customs-docs (POST /v1/declarations). Timeouts come from harbourline.customs-docs.*
 * (connect 1 s, read 4 s); retried max-retries times with a fixed back-off on I/O errors only.
 */
@Component
public class CustomsDocsClient {
    private static final Logger log = LoggerFactory.getLogger(CustomsDocsClient.class);

    private final RestClient http;
    private final CustomsDocsProperties props;

    public CustomsDocsClient(RestClient customsDocsRestClient, CustomsDocsProperties props) {
        this.http = customsDocsRestClient;
        this.props = props;
    }

    public String requestDeclaration(Booking b) {
        Consignee c = b.getConsignee();
        Map<String, Object> body = Map.of(
                "bookingRef", b.getBookingRef(),
                "exporterEori", String.valueOf(b.getShipper().getEori()),
                "origin", b.getLane().getOrigin(),
                "destination", b.getLane().getDestination(),
                "shipTo", Map.of("name", c.getContactName(), "company", String.valueOf(c.getCompanyName()),
                        "address", c.getAddressLine1(), "city", c.getCity(), "postcode", c.getPostcode(),
                        "country", c.getCountry(), "eori", String.valueOf(c.getEori())),
                "items", lines(b.getItems()));
        int attempt = 0;
        while (true) {
            try {
                Map<?, ?> resp = http.post().uri("/v1/declarations").body(body).retrieve().body(Map.class);
                return String.valueOf(resp.get("declarationRef"));
            } catch (ResourceAccessException e) {
                if (attempt++ >= props.maxRetries()) {
                    throw new CustomsDocsUnavailableException(e.getMessage());
                }
                log.warn("customs-docs attempt {} failed for {}: {}", attempt, b.getBookingRef(), e.getMessage());
                sleep(props.retryBackoff().toMillis());
            }
        }
    }

    private static List<Map<String, Object>> lines(List<CargoItem> items) {
        return items.stream().map(i -> Map.<String, Object>of(
                "description", i.getDescription(), "hsCode", String.valueOf(i.getHsCode()),
                "packages", i.getPackages(), "grossKg", i.getGrossKg(), "volumeM3", i.getVolumeM3())).toList();
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
