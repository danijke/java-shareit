package ru.practicum.shareit.error;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.practicum.shareit.exception.ForwardedServerException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ForwardedServerException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleServerError(ForwardedServerException e) {
        return Mono.just(ResponseEntity.status(e.getHttpStatus()).body(e.getError()));
    }

    @ExceptionHandler(Throwable.class)
    public Mono<ResponseEntity<ErrorResponse>> handleValidation(Throwable  e, ServerWebExchange exchange) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse body = ErrorResponse.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .detail(e.getMessage())
                .path(exchange.getRequest().getURI().getPath())
                .build();

        return Mono.just(ResponseEntity.status(status).body(body));
    }
}
