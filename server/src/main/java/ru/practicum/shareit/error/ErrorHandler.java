package ru.practicum.shareit.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.*;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(NotFoundException e, HttpServletRequest request) {
        return buildError(
                HttpStatus.NOT_FOUND,
                "Resource not found",
                String.format("resource: %s; resourceId: %d", e.getResource(), e.getResourceId()),
                request
        );
    }

    @ExceptionHandler(EmailConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmailConflict(EmailConflictException e, HttpServletRequest request) {
        return buildError(
                HttpStatus.CONFLICT,
                "Email already exits",
                String.format("email: %s", e.getEmail()),
                request
        );
    }

    @ExceptionHandler(UserPermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleUserPermission(UserPermissionException e, HttpServletRequest request) {
        return buildError(
                HttpStatus.FORBIDDEN,
                "Access Denied",
                String.format("%s; UserId: %d", e.getErr(), e.getUserId()),
                request
        );
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOther(final Throwable e, HttpServletRequest request) {
        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                e.getMessage(),
                request
        );
    }

    private ErrorResponse buildError(HttpStatus status, String err, String detail, HttpServletRequest request) {
        return ErrorResponse.builder()
                .status(status.value())
                .error(err)
                .detail(detail)
                .path(request.getRequestURI())
                .build();
    }
}
