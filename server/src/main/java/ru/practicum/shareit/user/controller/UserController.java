package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<UserDto> getUsers(Pageable pageable) {
        return userService.getUsers(pageable);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto dto) {
        return userService.postUser(dto);
    }

    @PatchMapping("/{id}")
    public UserDto patchUser(@PathVariable Long id, @RequestBody UserDto dto) {
        return userService.patchUser(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
