package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
@Slf4j
public class ItemRequestController {
    private final ItemRequestService service;

    @PostMapping
    public ItemRequestDto postRequest(@RequestBody ItemRequestDto dto) {
        log.info("request dto in server-controller {}", dto);
        return service.postRequest(dto);
    }

    @GetMapping
    public Collection<ItemRequestDto> getAllByUserId(
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return service.getAllByUserId(userId);
    }

    @GetMapping("/all")
    public Collection<ItemRequestDto> getAllByUserIdNot(
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return service.getAllByUserIdNot(userId);
    }

    @GetMapping("/{id}")
    public ItemRequestDto getById(@PathVariable Long id) {
        return service.getById(id);
    }
}

