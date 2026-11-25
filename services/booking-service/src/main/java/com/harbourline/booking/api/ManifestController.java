package com.harbourline.booking.api;

import com.harbourline.booking.domain.Manifest;
import com.harbourline.booking.service.ManifestService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/depots/{depotCode}/manifests")
public class ManifestController {
    private final ManifestService manifests;

    public ManifestController(ManifestService manifests) { this.manifests = manifests; }

    @GetMapping
    public List<Manifest> open(@PathVariable String depotCode) { return manifests.openAt(depotCode); }
}
