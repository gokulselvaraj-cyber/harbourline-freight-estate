package com.harbourline.booking.api;

import com.harbourline.booking.api.dto.ErrorBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorBody invalid(MethodArgumentNotValidException e) {
        return new ErrorBody("VALIDATION", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
