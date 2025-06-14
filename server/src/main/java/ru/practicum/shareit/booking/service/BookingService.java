package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.booking.model.BookingState;

import java.util.Collection;

public interface BookingService {
    BookingDto postBooking(BookingRequestDto dto);

    Collection<BookingDto> getAllByBooker(Long userId, BookingState state);

    BookingDto getByIdAndUserId(Long id, Long userId);

    BookingDto patchBooking(BookingRequestDto dto);

    Collection<BookingDto> getAllByOwner(Long userId, BookingState state);
}
