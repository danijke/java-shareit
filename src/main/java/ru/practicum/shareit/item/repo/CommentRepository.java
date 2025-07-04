package ru.practicum.shareit.item.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Comment;

import java.util.*;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Collection<Comment> findByItemId(Long itemId);

    Collection<Comment> findByItemIdIn(List<Long> ids);
}
