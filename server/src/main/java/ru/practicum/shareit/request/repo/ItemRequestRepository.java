package ru.practicum.shareit.request.repo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.Collection;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    @Query("select distinct r from ItemRequest r " +
            "left join fetch r.items " +
            "where r.requester.id = :userId " +
            "order by r.created desc")
    Collection<ItemRequest> findAllByRequesterIdWithItems(@Param("userId") Long userId);

    @Query("select distinct r from ItemRequest r " +
            "where r.requester.id <> :userId " +
            "order by r.created desc")
    Collection<ItemRequest> findAllByRequesterIdNot(@Param("userId") Long userId);
}
