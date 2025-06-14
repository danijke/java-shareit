package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;
import ru.practicum.shareit.request.client.ItemRequestClient;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestClient client;

    @PostMapping
    public Mono<ItemRequestDto> postRequest(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody ItemRequestDto dto
    ) {
        dto.setUserId(userId);
        log.info("request dto in controller {}", dto);
        return client.postRequest(dto);
    }

    @GetMapping
    public Flux<ItemRequestDto> getAllByUserId(
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return client.getAllByUserId(userId);
    }

    @GetMapping("/all")
    public Flux<ItemRequestDto> getAll(
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return client.getAllByUserIdNot(userId);
    }

    @GetMapping("/{id}")
    public Mono<ItemRequestDto> getById(@PathVariable Long id) {
        return client.getById(id);
    }
}
