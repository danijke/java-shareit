package ru.practicum.shareit.booking.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingRequestDto {
    Long id;
    LocalDateTime start;
    LocalDateTime end;
    Boolean status;
    Long itemId;
    Long userId;
}
