package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
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
    public ItemDto createItem(@RequestBody ItemDto dto) {
        log.info("dto in controller {}", dto);
        return itemService.postItem(dto);
    }

    @PatchMapping("/{id}")
    public ItemDto patchItem(
            @PathVariable Long id,
            @RequestBody ItemDto dto
    ) {
        dto.setId(id);
        return itemService.patchItem(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        itemService.deleteItem(id);
    }

    @PostMapping("/{id}/comment")
    public CommentDto postComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto dto
    ) {
        dto.setItemId(id);
        return itemService.postComment(dto);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(@RequestParam String text) {
        return itemService.searchByText(text);
    }
}
