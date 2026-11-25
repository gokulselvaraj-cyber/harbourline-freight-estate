package com.harbourline.booking.scheduler;

import com.harbourline.booking.domain.Manifest;
import com.harbourline.booking.repository.ManifestRepository;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Closes open cargo manifests after the dispatch cut-off (GB 14:00, EU 15:30 local). */
@Component
public class ManifestCloseJob {
    private final ManifestRepository manifests;

    public ManifestCloseJob(ManifestRepository manifests) { this.manifests = manifests; }

    @Scheduled(cron = "0 5 14 * * MON-FRI", zone = "Europe/London")
    @Transactional
    public void closeGb() { close(List.of("FXT", "IMM", "TSB")); }

    @Scheduled(cron = "0 35 15 * * MON-FRI", zone = "Europe/Amsterdam")
    @Transactional
    public void closeEu() { close(List.of("RTM", "ANT", "ZEE", "HAM", "GDY")); }

    private void close(List<String> depots) {
        depots.forEach(d -> manifests.findByDepotCodeAndClosedFalse(d).forEach(Manifest::close));
    }
}
