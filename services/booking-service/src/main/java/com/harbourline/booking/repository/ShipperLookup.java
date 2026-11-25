package com.harbourline.booking.repository;

import com.harbourline.booking.domain.Shipper;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class ShipperLookup {
    private final EntityManager em;

    public ShipperLookup(EntityManager em) { this.em = em; }

    public Shipper byAccount(String accountNo) {
        return em.createQuery("select s from Shipper s where s.accountNo = :a", Shipper.class)
                .setParameter("a", accountNo).getSingleResult();
    }
}
