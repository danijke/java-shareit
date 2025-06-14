package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {

    public static Booking toBooking(BookingRequestDto dto, Item item, User booker) {
        return Booking.builder()
                .start(dto.getStart())
                .end(dto.getEnd())
                .item(item)
                .booker(booker)
                .status(BookingStatus.WAITING)
                .build();
    }

    public static BookingDto toBookingDto(Booking saved) {
        return BookingDto.builder()
                .id(saved.getId())
                .start(saved.getStart())
                .end(saved.getEnd())
                .status(saved.getStatus())
                .item(ItemMapper.toItemDto(saved.getItem()))
                .booker(UserMapper.toUserDto(saved.getBooker()))
                .build();
    }
}
