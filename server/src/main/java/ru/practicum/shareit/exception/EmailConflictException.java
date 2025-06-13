package ru.practicum.shareit.exception;

import lombok.*;

@AllArgsConstructor
@Getter
public class EmailConflictException extends RuntimeException {
    String email;
}
