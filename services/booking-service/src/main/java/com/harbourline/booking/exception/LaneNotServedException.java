package com.harbourline.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class LaneNotServedException extends RuntimeException {
    public LaneNotServedException(String detail) {
        super("Lane not served: " + detail);
    }
}
