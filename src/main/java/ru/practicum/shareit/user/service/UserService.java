package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserPatchDto;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserService {
    Collection<User> getAllUsers();

    User getUser(Long id);

    User postUser(User newUser);

    UserPatchDto patchUser(UserPatchDto dto);

    void deleteUser(Long id);
}
