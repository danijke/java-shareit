package ru.practicum.shareit.exception;

import lombok.*;

@AllArgsConstructor
@Getter
public class ValidationException extends RuntimeException {
    String message;
}
