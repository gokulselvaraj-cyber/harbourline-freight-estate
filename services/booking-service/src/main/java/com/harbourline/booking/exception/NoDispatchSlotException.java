package com.harbourline.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NoDispatchSlotException extends RuntimeException {
    public NoDispatchSlotException(String detail) {
        super("No dispatch slot free at " + detail);
    }
}
