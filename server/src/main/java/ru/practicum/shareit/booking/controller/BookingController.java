package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    public Collection<BookingDto> getAllByBooker(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(defaultValue = "ALL") BookingState state
    ) {
        return bookingService.getAllByBooker(userId, state);
    }

    @GetMapping("/owner")
    public Collection<BookingDto> getAllByOwner(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(defaultValue = "ALL") BookingState state
    ) {
        return bookingService.getAllByOwner(userId, state);
    }

    @GetMapping("/{id}")
    public BookingDto getByUserId(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long id
    ) {
        return bookingService.getByIdAndUserId(id, userId);
    }

    @PostMapping
    public BookingDto createBooking(
            @RequestBody BookingRequestDto dto
    ) {
        return bookingService.postBooking(dto);
    }

    @PatchMapping("/{id}")
    public BookingDto patchBooking(
            @PathVariable Long id,
            @RequestBody BookingRequestDto dto
    ) {
        dto.setId(id);
        return bookingService.patchBooking(dto);
    }
}
