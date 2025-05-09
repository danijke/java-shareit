package ru.practicum.shareit.item.repo;

import ru.practicum.shareit.item.model.Item;

import java.util.*;

public interface ItemRepository {
    Collection<Item> findAllByUser(Long userId);

    Optional<Item> findItem(Long id);

    Item saveItem(Item newItem);

    void updateItem(Item item);

    void removeItem(Long id);

    Collection<Item> findByText(String text);
}
