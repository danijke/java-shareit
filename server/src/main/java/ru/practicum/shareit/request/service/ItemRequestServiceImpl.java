package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repo.ItemRequestRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository repository;
    private final UserService userService;

    @Override
    @Transactional
    public ItemRequestDto postRequest(ItemRequestDto dto) {
        UserDto requester = userService.getUserById(dto.getUserId());
        ItemRequest request = ItemRequestMapper.toItemRequest(dto, requester);
        return ItemRequestMapper.toItemRequestDto(repository.save(request));
    }

    @Override
    public Collection<ItemRequestDto> getAllByUserId(Long userId) {
        return repository.findAllByRequesterIdWithItems(userId).stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .toList();
    }

    @Override
    public Collection<ItemRequestDto> getAllByUserIdNot(Long userId) {
        return repository.findAllByRequesterIdNot(userId).stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .toList();
    }

    @Override
    public ItemRequestDto getById(Long id) {
        return repository.findById(id)
                .map(ItemRequestMapper::toItemRequestDto)
                .orElseThrow(() -> new NotFoundException(ItemRequest.class.getSimpleName(), id));
    }
}
