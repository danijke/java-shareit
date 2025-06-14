package ru.practicum.shareit.request.mapper;

import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.*;

public class ItemRequestMapper {

    public static ItemRequest toItemRequest(ItemRequestDto dto, UserDto requester) {
        return ItemRequest.builder()
                .description(dto.getDescription())
                .created(LocalDateTime.now())
                .requester(UserMapper.toUser(requester))
                .build();
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest save) {
        return ItemRequestDto.builder()
                .id(save.getId())
                .description(save.getDescription())
                .created(save.getCreated())
                .items(Optional.ofNullable(save.getItems())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(ItemMapper::toItemDto)
                        .toList())
                .build();
    }
}
