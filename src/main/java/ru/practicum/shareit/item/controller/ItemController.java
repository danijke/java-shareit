package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.validation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public Collection<ItemDto> getItemsByUser(
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemService.getItemsByUser(userId);
    }

    @GetMapping("/{id}")
    public ItemDto getItem(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @PostMapping
    public ItemDto createItem(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @Validated(OnPost.class) @RequestBody ItemDto dto
    ) {
        dto.setOwnerId(userId);
        return itemService.postItem(dto);
    }

    @PatchMapping("/{id}")
    public ItemDto patchItem(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long id,
            @Validated(OnPatch.class) @RequestBody ItemDto dto
    ) {
        dto.setId(id);
        return itemService.patchItem(userId, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        itemService.deleteItem(id);
    }

    @PostMapping("/{id}/comment")
    public CommentDto postComment(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long id,
            @Valid @RequestBody CommentDto dto
    ) {
        return itemService.postComment(dto, id, userId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(@RequestParam String text) {
        return itemService.searchByText(text);
    }
}
