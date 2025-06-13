package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;
import ru.practicum.shareit.item.client.ItemClient;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.validation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/items")
public class ItemController {
    private final ItemClient client;

    @GetMapping
    public Flux<ItemDto> getItemsByUser(
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return client.getItemsByUser(userId);
    }

    @GetMapping("/{id}")
    public Mono<ItemDto> getItem(@PathVariable Long id) {
        return client.getItemById(id);
    }

    @PostMapping
    public Mono<ItemDto> createItem(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @Validated(OnPost.class) @RequestBody ItemDto dto
    ) {
        dto.setOwnerId(userId);
        return client.postItem(dto);
    }

    @PatchMapping("/{id}")
    public Mono<ItemDto> patchItem(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long id,
            @Validated(OnPatch.class) @RequestBody ItemDto dto
    ) {
        dto.setId(id);
        dto.setOwnerId(userId);
        return client.patchItem(dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteItem(@PathVariable Long id) {
        return client.deleteItem(id);
    }

    @PostMapping("/{id}/comment")
    public Mono<CommentDto> postComment(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long id,
            @RequestBody CommentDto dto
    ) {
        CommentRequestDto requestDto = CommentRequestDto.builder()
                .dto(dto)
                .itemId(id)
                .bookerId(userId)
                .build();

        return client.postComment(requestDto);
    }

    @GetMapping("/search")
    public Flux<ItemDto> search(@RequestParam String text) {
        return client.searchByText(text);
    }
}
