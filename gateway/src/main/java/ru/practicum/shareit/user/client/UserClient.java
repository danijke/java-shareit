package ru.practicum.shareit.user.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.*;
import ru.practicum.shareit.client.BaseWebClient;
import ru.practicum.shareit.user.dto.UserDto;

import java.awt.print.Pageable;
import java.util.Collection;

@Component
public class UserClient extends BaseWebClient<UserDto> {
    private static final String PATH = "/users";

    private static final String PATH_WITH_VARS = PATH + "/{id}";

    public UserClient(WebClient webClient) {
        super(webClient, UserDto.class);
    }

    public Flux<UserDto> getUsers(Pageable pageable) {
        return getFlux(PATH, pageable);
    }

    public Mono<UserDto> getUserById(Long id) {
        return get(PATH_WITH_VARS, id);
    }

    public Mono<UserDto> postUser(UserDto dto) {
        return post(PATH, dto, UserDto.class);
    }

    public Mono<UserDto> patchUser(UserDto dto) {
        return patch(PATH_WITH_VARS, dto, dto.getId());
    }

    public Mono<Void> deleteUser(Long id) {
        return delete(PATH_WITH_VARS, id);
    }
}
