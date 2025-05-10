package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public Collection<UserDto> getAllUsers() {
        return repository.findAll().stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    public UserDto getUserById(Long id) {
        return UserMapper.toUserDto(getUser(id));
    }

    @Override
    public UserDto postUser(UserDto dto) {
        log.info("dto_user after validating: {}", dto);
        checkEmailExists(dto.getEmail());
        return UserMapper.toUserDto(repository.saveUser(UserMapper.toUser(dto)));
    }

    @Override
    public UserDto patchUser(UserDto dto) {
        if (dto.getEmail() != null) checkEmailExists(dto.getEmail());

        User user = getUser(dto.getId());
        UserMapper.patch(user, dto);
        repository.updateUser(user);
        return dto;
    }

    @Override
    public void deleteUser(Long id) {
        repository.removeUser(id);
    }

    private User getUser(Long id) {
        return repository.findUser(id)
                .orElseThrow(() -> new NotFoundException(User.class.getSimpleName(), id));
    }

    private void checkEmailExists(String email) {
        if (repository.emailExits(email)) {
            throw new EmailConflictException(email);
        }
    }
}
