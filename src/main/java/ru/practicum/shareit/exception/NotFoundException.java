package ru.practicum.shareit.exception;

import lombok.*;

@AllArgsConstructor
@Getter
public class NotFoundException extends RuntimeException {
    String resource;
    Long resourceId;
}
