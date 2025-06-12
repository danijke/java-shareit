package ru.practicum.shareit.user.client;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.*;
import ru.practicum.shareit.client.BaseWebClient;
import ru.practicum.shareit.user.dto.UserDto;

import java.awt.print.Pageable;
import java.util.Collection;


public class UserClient extends BaseWebClient {

    private static final String PATH = "/users";

    public UserClient(WebClient webClient) {
        super(webClient);
    }


    public Flux<UserDto> getUsers(Pageable pageable) {
        return getFlux(PATH, UserDto.class, pageable);
    }

    public Mono<UserDto> getUserById(Long id) {
        return get(PATH + "/{id}", UserDto.class, id);
    }

    public Mono<UserDto> postUser(UserDto dto) {
        return post(PATH, dto, UserDto.class);
    }

    public Mono<UserDto> patchUser(UserDto dto) {
        return patch(PATH + "/{id}", dto, UserDto.class, dto.getId());
    }

    public Mono<Void> deleteUser(Long id) {
        return delete(PATH + "/{id}", id);
    }
}
