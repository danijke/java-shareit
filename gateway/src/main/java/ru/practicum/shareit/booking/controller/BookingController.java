package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.practicum.shareit.booking.client.BookingClient;
import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.client.ItemClient;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingClient client;

    @GetMapping
    public Flux<BookingDto> getAllByBooker(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(defaultValue = "ALL") String state
    ) {
        return client.getAllByBooker(userId, state);
    }

    @GetMapping("/owner")
    public Collection<BookingDto> getAllByOwner(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(defaultValue = "ALL") String state
    ) {
        return client.getAllByOwner(userId, state);
    }

    @GetMapping("/{id}")
    public BookingDto getByUserId(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long id
    ) {
        return client.getByIdAndUserId(id, userId);
    }

    @PostMapping
    public BookingDto createBooking(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody BookingRequestDto dto
    ) {
        dto.setBookerId(userId);
        return client.postBooking(dto);
    }

    @PatchMapping("/{id}")
    public BookingDto patchBooking(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long id,
            @RequestParam Boolean approved
    ) {
        return client.patchBooking(id, userId, approved);
    }
}
