package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.*;

import java.util.Collection;

public interface ItemService {
    Collection<ItemDto> getItemsByUser(Long userId);

    ItemDto getItemById(Long id);

    ItemDto postItem(ItemDto dto);

    ItemDto patchItem(Long userId, ItemDto dto);

    void deleteItem(Long id);

    Collection<ItemDto> searchByText(String text);

    CommentDto postComment(CommentDto dto, Long id, Long userId);
}
