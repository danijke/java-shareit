package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public Collection<UserDto> getUsers(Pageable pageable) {
        return repository.findAll(pageable).stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        return UserMapper.toUserDto(getUser(id));
    }

    @Override
    @Transactional
    public UserDto postUser(UserDto dto) {
        checkEmailExists(dto);
        return UserMapper.toUserDto(repository.save(UserMapper.toUser(dto)));
    }

    @Override
    @Transactional
    public UserDto patchUser(UserDto dto) {
        checkEmailExists(dto);
        User user = getUser(dto.getId());
        UserMapper.patch(user, dto);
        return UserMapper.toUserDto(repository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void checkUserExists(Long userId) {
        if (!repository.existsById(userId)) {
            throw new NotFoundException(User.class.getSimpleName(), userId);
        }
    }

    private User getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(User.class.getSimpleName(), id));
    }

    private void checkEmailExists(UserDto dto) {
        repository.findByEmail(dto.getEmail())
                .ifPresent(existingUser -> {
                    if (dto.getId() == null || !existingUser.getId().equals(dto.getId())) {
                        log.info("Email conflict for dto: {}", dto);
                        throw new EmailConflictException(dto.getEmail());
                    }
                });
    }
}
