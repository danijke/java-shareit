package ru.practicum.shareit.booking.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDto {
    Long id;
    LocalDateTime start;
    LocalDateTime end;
    String status;
    ItemDto item;
    UserDto booker;
}
