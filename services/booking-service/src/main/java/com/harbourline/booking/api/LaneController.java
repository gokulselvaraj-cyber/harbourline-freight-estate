package com.harbourline.booking.api;

import com.harbourline.booking.domain.Lane;
import com.harbourline.booking.service.LaneService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/lanes")
public class LaneController {
    private final LaneService lanes;

    public LaneController(LaneService lanes) { this.lanes = lanes; }

    @GetMapping
    public List<Lane> list() { return lanes.activeLanes(); }
}
