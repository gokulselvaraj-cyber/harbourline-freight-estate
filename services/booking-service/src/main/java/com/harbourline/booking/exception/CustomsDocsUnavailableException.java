package com.harbourline.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class CustomsDocsUnavailableException extends RuntimeException {
    public CustomsDocsUnavailableException(String detail) {
        super("customs-docs unavailable: " + detail);
    }
}
