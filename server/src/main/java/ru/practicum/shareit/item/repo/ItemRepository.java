package ru.practicum.shareit.item.repo;

import org.springframework.data.jpa.repository.*;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @EntityGraph(attributePaths = "items")
    Collection<Item> findAllByOwnerId(Long userId);

    @Query("select i from Item i " +
            "where i.available = true " +
            "and (lower(i.name) like lower(concat('%', ?1, '%')) " +
            "or lower(i.description) like lower(concat('%', ?1, '%')))")
    Collection<Item> findAvailableByText(String text);
}
