package ru.practicum.shareit.item.repo;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Collection<Item> findAllByUser(Long userId) {
        return items.values().stream()
                .filter(item -> item.getOwner().getId().equals(userId))
                .toList();
    }

    @Override
    public Optional<Item> findItem(Long id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public Item saveItem(Item newItem) {
        newItem.setId(getNextId());
        items.put(newItem.getId(), newItem);
        return newItem;
    }

    @Override
    public void updateItem(Item item) {
        items.put(item.getId(), item);
    }

    @Override
    public void removeItem(Long id) {
        items.remove(id);
    }

    @Override
    public Collection<Item> findByText(String text) {
        if (text.isBlank()) return List.of();
        return items.values().stream()
                .filter(item -> (item.getName().toUpperCase().contains(text) ||
                        item.getDescription().toUpperCase().contains(text)) &&
                        item.getAvailable()
                )
                .toList();
    }

    private long getNextId() {
        long currentMaxId = items.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
