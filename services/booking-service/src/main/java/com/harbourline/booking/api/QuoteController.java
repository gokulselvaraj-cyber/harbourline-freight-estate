package com.harbourline.booking.api;

import com.harbourline.booking.api.dto.QuoteRequest;
import com.harbourline.booking.domain.*;
import com.harbourline.booking.exception.LaneNotServedException;
import com.harbourline.booking.repository.LaneRepository;
import com.harbourline.booking.service.RatingService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/quotes")
public class QuoteController {
    private final RatingService rating;
    private final LaneRepository lanes;

    public QuoteController(RatingService rating, LaneRepository lanes) {
        this.rating = rating;
        this.lanes = lanes;
    }

    @PostMapping
    public Quote quote(@Valid @RequestBody QuoteRequest req) {
        Lane lane = lanes.findActive(req.origin(), req.destination(), TransportMode.valueOf(req.mode()))
                .orElseThrow(() -> new LaneNotServedException(req.origin() + "-" + req.destination()));
        List<CargoItem> items = req.items() == null ? List.of() : req.items().stream()
                .map(i -> new CargoItem(i.description(), i.hsCode(), i.packages(), i.grossKg(), i.volumeM3(), i.dangerous()))
                .toList();
        return rating.quote(lane, Equipment.fromCode(req.equipment()), req.units(), items,
                req.onDate() == null ? LocalDate.now() : req.onDate());
    }
}
