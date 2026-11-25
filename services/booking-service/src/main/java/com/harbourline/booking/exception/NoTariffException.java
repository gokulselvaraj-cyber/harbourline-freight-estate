package com.harbourline.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class NoTariffException extends RuntimeException {
    public NoTariffException(String detail) {
        super("No tariff for " + detail);
    }
}
