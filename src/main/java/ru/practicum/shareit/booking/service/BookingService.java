package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.booking.model.BookingState;

import java.util.Collection;

public interface BookingService {
    BookingDto postBooking(Long userId, BookingRequestDto dto);

    Collection<BookingDto> getAllByBooker(Long userId, BookingState state);

    BookingDto getByIdAndUserId(Long id, Long userId);

    BookingDto patchBooking(Long id, Long userId, Boolean approved);

    Collection<BookingDto> getAllByOwner(Long userId, BookingState state);
}
