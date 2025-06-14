package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import ru.practicum.shareit.error.ErrorResponse;

public class ForwardedServerException extends RuntimeException {
    private final ErrorResponse error;

    public ForwardedServerException(ErrorResponse er) {
        super(er.detail());
        this.error = er;
    }

    public ErrorResponse getError() {
        return error;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.valueOf(error.status());
    }
}

