package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

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
    public ItemDto create(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @Valid @RequestBody Item newItem
    ) {
        return itemService.postItem(userId, newItem);
    }

    @PatchMapping("/{id}")
    public ItemDto patchItem(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long id,
            @Valid @RequestBody ItemDto dto
    ) {
        dto.setId(id);
        return itemService.patchItem(userId, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        itemService.deleteItem(id);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(@RequestParam String text) {
        return itemService.searchByText(text);
    }
}
