package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.dto.UserPatchDto;
import ru.practicum.shareit.user.model.User;

public class UserMapper {

    public static void patch(User user, UserPatchDto dto) {
        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
    }
}
