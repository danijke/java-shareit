package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;
import ru.practicum.shareit.user.client.UserClient;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.validation.*;

import java.awt.print.Pageable;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserClient client;

    @GetMapping
    public Flux<UserDto> getUsers(Pageable pageable) {
        return client.getUsers(pageable);
    }

    @GetMapping("/{id}")
    public Mono<UserDto> getUser(@PathVariable Long id) {
        return client.getUserById(id);
    }

    @PostMapping
    public Mono<UserDto> create(@Validated(OnPost.class) @RequestBody UserDto dto) {
        return client.postUser(dto);
    }

    @PatchMapping("/{id}")
    public Mono<UserDto> patchUser(
            @PathVariable Long id,
            @Validated(OnPatch.class) @RequestBody UserDto dto
    ) {
        dto.setId(id);
        return client.patchUser(dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable Long id) {
        client.deleteUser(id);
    }

}
