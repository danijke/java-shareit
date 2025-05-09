package ru.practicum.shareit.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserPermissionException extends RuntimeException {
    public UserPermissionException(String s) {
        super(s);
    }
}
