package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.user.dto.UserPatchDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public Collection<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return repository.findUser(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public User postUser(User newUser) {
        checkEmailExists(newUser.getEmail());
        return repository.saveUser(newUser);
    }

    @Override
    public UserPatchDto patchUser(UserPatchDto dto) {
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

    private void checkEmailExists(String email) {
        if (repository.emailExits(email)) {
            throw new EmailConflictException();
        }
    }
}
