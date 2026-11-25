package com.harbourline.booking.service;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;

/** Booking refs look like HLF-26-7Q4K2M (prefix, year, 6 chars without I/O/0/1). */
@Component
public class ReferenceGenerator {
    private static final char[] ALPHABET = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ".toCharArray();
    private final SecureRandom rnd = new SecureRandom();

    public String next(int year) {
        StringBuilder sb = new StringBuilder("HLF-").append(year % 100).append('-');
        for (int i = 0; i < 6; i++) sb.append(ALPHABET[rnd.nextInt(ALPHABET.length)]);
        return sb.toString();
    }
}
