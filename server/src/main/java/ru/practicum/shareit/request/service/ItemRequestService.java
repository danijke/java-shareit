package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.Collection;

public interface ItemRequestService {
    ItemRequestDto postRequest(ItemRequestDto dto);

    Collection<ItemRequestDto> getAllByUserId(Long userId);

    Collection<ItemRequestDto> getAllByUserIdNot(Long userId);

    ItemRequestDto getById(Long id);
}
