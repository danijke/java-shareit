package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;
import ru.practicum.shareit.user.client.UserClient;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.validation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserClient client;

    @GetMapping
    public Flux<UserDto> getUsers() {
        return client.getUsers();
    }

    @GetMapping("/{id}")
    public Mono<UserDto> getUser(@PathVariable Long id) {
        return client.getUserById(id);
    }

    @PostMapping
    public Mono<UserDto> create(@Validated(OnPost.class) @RequestBody UserDto dto) {
        log.info("user dto in controller {}", dto);
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
        return client.deleteUser(id);
    }

}
