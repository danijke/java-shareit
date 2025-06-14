package ru.practicum.shareit.request.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.*;
import ru.practicum.shareit.client.BaseWebClient;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@Component
public class ItemRequestClient extends BaseWebClient<ItemRequestDto> {
    private static final String PATH = "/requests";

    public ItemRequestClient(WebClient webClient) {
        super(webClient, ItemRequestDto.class);
    }

    public Mono<ItemRequestDto> postRequest(ItemRequestDto dto) {
        return post(PATH, dto);
    }

    public Flux<ItemRequestDto> getAllByUserId(Long userId) {
        return getFluxWithHeader(PATH, userId);
    }

    public Flux<ItemRequestDto> getAllByUserIdNot(Long userId) {
        return getFluxWithHeader(PATH + "/all", userId);
    }

    public Mono<ItemRequestDto> getById(Long id) {
        return get(PATH + "/{id}", id);
    }
}
