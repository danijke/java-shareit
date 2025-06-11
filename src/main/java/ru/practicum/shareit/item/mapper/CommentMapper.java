package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

public class CommentMapper {
    public static Comment toComment(CommentDto dto, Item item, User user) {
        return Comment.builder()
                .text(dto.getText())
                .item(item)
                .author(user)
                .created(LocalDateTime.now())
                .build();
    }

    public static CommentDto toCommentDto(Comment saved) {
        return CommentDto.builder()
                .id(saved.getId())
                .text(saved.getText())
                .authorName(saved.getAuthor().getName())
                .created(saved.getCreated())
                .build();
    }
}
