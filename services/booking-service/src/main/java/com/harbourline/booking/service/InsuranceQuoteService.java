package com.harbourline.booking.service;

import com.harbourline.booking.domain.Booking;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

/**
 * Cargo insurance quotes. Planned for 2027 (HF-2302) together with the underwriting partner;
 * the portal hides the "insure this shipment" tick box until then.
 */
@Service
public class InsuranceQuoteService {
    public BigDecimal quote(Booking booking) {
        throw new UnsupportedOperationException("Cargo insurance is not available yet (HF-2302)");
    }
}
