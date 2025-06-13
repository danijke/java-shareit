package ru.practicum.shareit.booking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;
import ru.practicum.shareit.booking.client.BookingClient;
import ru.practicum.shareit.booking.dto.*;

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
    public Flux<BookingDto> getAllByOwner(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(defaultValue = "ALL") String state
    ) {
        return client.getAllByOwner(userId, state);
    }

    @GetMapping("/{id}")
    public Mono<BookingDto> getByUserId(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long id
    ) {
        return client.getByIdAndUserId(id, userId);
    }

    @PostMapping
    public Mono<BookingDto> createBooking(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @Valid @RequestBody BookingRequestDto dto
    ) {
        dto.setUserId(userId);
        return client.postBooking(dto);
    }

    @PatchMapping("/{id}")
    public Mono<BookingDto> patchBooking(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long id,
            @RequestParam Boolean approved
    ) {
        BookingRequestDto dto = BookingRequestDto.builder()
                .id(id)
                .userId(userId)
                .status(approved)
                .build();

        return client.patchBooking(dto);
    }
}
