package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserService {
    Collection<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto postUser(UserDto dto);

    UserDto patchUser(UserDto dto);

    void deleteUser(Long id);
}
