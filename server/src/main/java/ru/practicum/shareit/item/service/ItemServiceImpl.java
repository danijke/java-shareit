package ru.practicum.shareit.item.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.booking.repo.BookingRepository;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.mapper.*;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.item.repo.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private final ItemRepository repository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public Collection<ItemDto> getItemsByUser(Long userId) {
        List<Item> items = (List<Item>) repository.findAllByOwnerId(userId);

        List<Long> ids = items.stream()
                .map(Item::getId)
                .toList();

        Collection<Comment> comments = commentRepository.findByItemIdIn(ids);
        Map<Long, List<Comment>> commentMap = comments.stream()
                .collect(Collectors.groupingBy(c -> c.getItem().getId()));
        return items.stream()
                .map(item -> {
                    List<Comment> itemComments = commentMap.getOrDefault(item.getId(), List.of());
                    return ItemMapper.toItemDtoWithComment(item, itemComments);
                })
                .toList();
    }

    @Override
    public ItemDto getItemById(Long id) {
        List<Comment> comments = (List<Comment>) commentRepository.findByItemId(id);

        return ItemMapper.toItemDtoWithComment(getItem(id), comments);
    }

    @Override
    @Transactional
    public ItemDto postItem(ItemDto dto) {
        Item item = ItemMapper.toItem(dto, userService.getUserById(dto.getOwnerId()));
        return ItemMapper.toItemDto(repository.save(item));
    }

    @Override
    @Transactional
    public ItemDto patchItem(ItemDto dto) {
        Item item = getItem(dto.getId());
        if (!item.getOwner().getId().equals(dto.getOwnerId())) {
            throw new UserPermissionException("User has not permission", dto.getOwnerId());
        }
        ItemMapper.patch(item, dto);
        return ItemMapper.toItemDto(repository.save(item));
    }

    @Override
    @Transactional
    public void deleteItem(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Collection<ItemDto> searchByText(String text) {
        if (StringUtils.isBlank(text)) {
            return List.of();
        }
        return repository
                .findAvailableByText(text)
                .stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Override
    @Transactional
    public CommentDto postComment(CommentRequestDto dto) {
        Item item = getItem(dto.getItemId());
        UserDto userDto = userService.getUserById(dto.getBookerId());

        QBooking booking = QBooking.booking;

        BooleanExpression predicate = booking.item.id.eq(dto.getItemId())
                .and(booking.booker.id.eq(dto.getBookerId()))
                .and(booking.end.before(LocalDateTime.now()))
                .and(booking.status.eq(BookingStatus.APPROVED));

        if (!bookingRepository.exists(predicate)) {
            throw new ValidationException(String.format("Item not available for comment; ItemId: %d", dto.getItemId()));
        }

        Comment saved = commentRepository.save(CommentMapper.toComment(dto.getText(), item, UserMapper.toUser(userDto)));

        return CommentMapper.toCommentDto(saved);
    }

    private Item getItem(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(Item.class.getSimpleName(), id));
    }
}
