package com.harbourline.booking.service;

import com.harbourline.booking.domain.Lane;
import com.harbourline.booking.repository.LaneRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LaneService {
    private final LaneRepository lanes;

    public LaneService(LaneRepository lanes) { this.lanes = lanes; }

    public List<Lane> activeLanes() {
        return lanes.findAll().stream().filter(Lane::isActive).toList();
    }
}
