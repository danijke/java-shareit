package ru.practicum.shareit.error;

import org.springframework.http.*;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.*;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(final NotFoundException e) {
        ErrorResponse response = ErrorResponse.create(e, HttpStatus.NOT_FOUND, "Not Found");
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @ExceptionHandler(EmailConflictException.class)
    public ResponseEntity<ErrorResponse> handleEmailConflict(final EmailConflictException e) {
        ErrorResponse response = ErrorResponse.create(e, HttpStatus.CONFLICT, "Email already exits");
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @ExceptionHandler(UserPermissionException.class)
    public ResponseEntity<ErrorResponse> handleUserPermission(final UserPermissionException e) {
        ErrorResponse response = ErrorResponse.create(e, HttpStatus.CONFLICT, "Access denied");
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
