package ru.practicum.shareit.exception;

import lombok.*;

@AllArgsConstructor
@Getter
public class UserPermissionException extends RuntimeException {
    String err;
    Long userId;
}
