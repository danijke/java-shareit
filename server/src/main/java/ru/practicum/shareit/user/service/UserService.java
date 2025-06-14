package ru.practicum.shareit.user.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserService {
    Collection<UserDto> getUsers(Pageable pageable);

    UserDto getUserById(Long id);

    UserDto postUser(UserDto dto);

    UserDto patchUser(UserDto dto);

    void deleteUser(Long id);

    void checkUserExists(Long userId);
}
