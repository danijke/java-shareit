package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.List;

public class ItemMapper {

    public static void patch(Item item, ItemDto dto) {
        if (dto.getName() != null) item.setName(dto.getName());
        if (dto.getDescription() != null) item.setDescription(dto.getDescription());
        if (dto.getAvailable() != null) item.setAvailable(dto.getAvailable());
    }

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .ownerId(item.getOwner().getId())
                .build();
    }

    public static Item toItem(ItemDto dto, UserDto owner) {
        return Item.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .available(dto.getAvailable())
                .owner(UserMapper.toUser(owner))
                .build();
    }

    public static ItemDto toItemDtoWithComment(Item item, List<Comment> comments) {
        ItemDto itemDto = toItemDto(item);
        List<CommentDto> dtoComments = comments.stream().map(CommentMapper::toCommentDto).toList();
        itemDto.setComments(dtoComments);
        return itemDto;
    }
}
