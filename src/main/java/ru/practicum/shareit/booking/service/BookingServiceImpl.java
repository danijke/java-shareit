package ru.practicum.shareit.booking.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.booking.repo.BookingRepository;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingRepository repository;
    private final ItemService itemService;
    private final UserService userService;

    @Override
    @Transactional
    public BookingDto postBooking(Long userId, BookingRequestDto dto) {
        User booker = UserMapper.toUser(userService.getUserById(userId));
        ItemDto item = itemService.getItemById(dto.getItemId());

        if (!item.getAvailable()) {
            throw new ValidationException(String.format("item is not available for booking; itemId : %d", item.getId()));
        }


        UserDto owner = userService.getUserById(item.getOwnerId());
        Booking saved = repository.save(BookingMapper.toBooking(dto, ItemMapper.toItem(item, owner), booker));

        return BookingMapper.toBookingDto(saved);
    }

    @Override
    public Collection<BookingDto> getAllByBooker(Long userId, BookingState state) {
        userService.checkUserExists(userId);
        BooleanExpression expr = QBooking.booking.booker.id.eq(userId);

        return getAllByPredicate(state.toPredicate(expr));
    }

    @Override
    public Collection<BookingDto> getAllByOwner(Long userId, BookingState state) {
        userService.checkUserExists(userId);
        BooleanExpression expr = QBooking.booking.item.owner.id.eq(userId);

        return getAllByPredicate(state.toPredicate(expr));
    }

    @Override
    public BookingDto getByIdAndUserId(Long id, Long userId) {
        return BookingMapper.toBookingDto(repository.findByIdAndBookerOrOwner(id, userId)
                .orElseThrow(() -> new UserPermissionException("User is not owner or booker", userId)));
    }

    @Override
    public BookingDto patchBooking(Long id, Long userId, Boolean approved) {
        Booking booking = repository.findById(id)
                .map(b -> {
                    if (!b.getItem().getOwner().getId().equals(userId)) {
                        throw new UserPermissionException("User is not owner", userId);
                    }

                    BookingStatus newStatus = approved ? BookingStatus.APPROVED : BookingStatus.REJECTED;
                    b.setStatus(newStatus);
                    return b;
                })
                .orElseThrow(() -> new NotFoundException(Booking.class.getSimpleName(), id));
        return BookingMapper.toBookingDto(booking);
    }

    private Collection<BookingDto> getAllByPredicate(BooleanExpression expr) {
        Sort sort = Sort.by(Sort.Direction.DESC, "start");

        return StreamSupport.stream(repository.findAll(expr, sort).spliterator(), false)
                .map(BookingMapper::toBookingDto)
                .toList();
    }
}
