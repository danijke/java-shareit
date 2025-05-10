package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private final ItemRepository repository;

    @Override
    public Collection<ItemDto> getItemsByUser(Long userId) {
        return repository.findAllByUser(userId)
                .stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Override
    public ItemDto getItemById(Long id) {
        return ItemMapper.toItemDto(getItem(id));
    }

    @Override
    public ItemDto postItem(Long userId, ItemDto dto) {
        Item item = ItemMapper.toItem(dto, UserMapper.toUser(userService.getUserById(userId)));
        return ItemMapper.toItemDto(repository.saveItem(item));
    }

    @Override
    public ItemDto patchItem(Long userId, ItemDto dto) {
        Item item = getItem(dto.getId());
        if (!item.getOwner().getId().equals(userId)) {
            throw new UserPermissionException("User has not permission", userId);
        }
        ItemMapper.patch(item, dto);
        repository.updateItem(item);
        return dto;
    }

    @Override
    public void deleteItem(Long id) {
        repository.removeItem(id);
    }

    @Override
    public Collection<ItemDto> searchByText(String text) {
        return repository.findByText(text)
                .stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    private Item getItem(Long id) {
        return repository.findItem(id)
                .orElseThrow(() -> new NotFoundException(Item.class.getSimpleName(), id));
    }
}
