package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;

public interface ItemService {
    Collection<ItemDto> getItemsByUser(Long userId);

    ItemDto getItemById(Long id);

    ItemDto postItem(Long userId, ItemDto dto);

    ItemDto patchItem(Long userId, ItemDto dto);

    void deleteItem(Long id);

    Collection<ItemDto> searchByText(String text);
}
