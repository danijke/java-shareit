package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.validation.StartBeforeEnd;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@StartBeforeEnd
public class BookingRequestDto {
    Long itemId;

    @FutureOrPresent
    @NotNull
    LocalDateTime start;

    @FutureOrPresent
    @NotNull
    LocalDateTime end;
}
