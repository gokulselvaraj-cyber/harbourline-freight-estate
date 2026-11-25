package com.harbourline.booking.legacy;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Old EDIFACT IFTMIN (booking message) importer from the FTP drop at hf-ftp-01. The EDI gateway now
 * posts to /v1/bookings directly (2025), so nothing calls this any more.
 */
public class EdifactIftminImporter {

    public List<String[]> parse(Path file) throws IOException {
        List<String[]> segments = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(file)) {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = r.read()) != -1) {
                if (c == '\'') {
                    segments.add(sb.toString().split("\\+"));
                    sb.setLength(0);
                } else if (c != '\n' && c != '\r') {
                    sb.append((char) c);
                }
            }
        }
        return segments;
    }

    public String bookingRef(List<String[]> segments) {
        for (String[] s : segments) {
            if ("BGM".equals(s[0]) && s.length > 2) return s[2];
        }
        return null;
    }
}
