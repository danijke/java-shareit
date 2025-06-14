package ru.practicum.shareit.item.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.*;
import ru.practicum.shareit.client.BaseWebClient;
import ru.practicum.shareit.item.dto.*;

import java.util.Map;

@Component
public class ItemClient extends BaseWebClient<ItemDto> {
    private static final String PATH = "/items";

    private static final String PATH_WITH_VARS = PATH + "/{id}";

    public ItemClient(WebClient webClient) {
        super(webClient, ItemDto.class);
    }

    public Flux<ItemDto> getItemsByUser(Long userId) {
        return getFluxWithHeader(PATH, userId);
    }

    public Mono<ItemDto> getItemById(Long id) {
        return get(PATH_WITH_VARS, id);
    }

    public Mono<ItemDto> postItem(ItemDto dto) {
        return post(PATH, dto);
    }

    public Mono<ItemDto> patchItem(ItemDto dto) {
        return patch(PATH_WITH_VARS, dto, dto.getId());
    }

    public Mono<Void> deleteItem(Long id) {
        return delete(PATH_WITH_VARS, id);
    }


    public Mono<CommentDto> postComment(CommentRequestDto requestDto) {
        return post(PATH_WITH_VARS + "/comment", requestDto, CommentDto.class, requestDto.getItemId());
    }

    public Flux<ItemDto> searchByText(String text) {
        return getFluxWithParams(PATH + "/search", Map.of("text", text));
    }
}
