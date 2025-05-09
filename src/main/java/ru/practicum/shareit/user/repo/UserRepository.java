package ru.practicum.shareit.user.repo;

import ru.practicum.shareit.user.model.User;

import java.util.*;

public interface UserRepository {
    Collection<User> findAll();

    Optional<User> findUser(Long id);

    User saveUser(User newUser);

    void updateUser(User user);

    void removeUser(Long id);

    boolean emailExits(String email);
}
