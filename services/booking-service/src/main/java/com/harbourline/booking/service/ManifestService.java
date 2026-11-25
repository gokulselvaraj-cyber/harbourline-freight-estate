package com.harbourline.booking.service;

import com.harbourline.booking.domain.Manifest;
import com.harbourline.booking.repository.ManifestRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** Read side for cargo manifests (shown to depot staff). */
@Service
public class ManifestService {
    private final ManifestRepository manifests;

    public ManifestService(ManifestRepository manifests) { this.manifests = manifests; }

    public List<Manifest> openAt(String depotCode) {
        return manifests.findByDepotCodeAndClosedFalse(depotCode);
    }
}
