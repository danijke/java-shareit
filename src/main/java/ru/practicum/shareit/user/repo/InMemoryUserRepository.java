package ru.practicum.shareit.user.repo;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public Optional<User> findUser(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User saveUser(User newUser) {
        newUser.setId(getNextId());
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public void updateUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void removeUser(Long id) {
        users.remove(id);
    }

    @Override
    public boolean emailExits(String email) {
        return users.values()
                .stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
