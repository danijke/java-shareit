package ru.practicum.shareit.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailConflictException extends RuntimeException {
    public EmailConflictException(String s) {
        super(s);
    }
}
