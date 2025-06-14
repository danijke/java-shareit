package ru.practicum.shareit.booking.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.*;
import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.client.BaseWebClient;

import java.util.Map;

@Component
public class BookingClient extends BaseWebClient<BookingDto> {
    private static final String PATH = "/bookings";

    private static final String PATH_WITH_VARS = PATH + "/{id}";

    public BookingClient(WebClient webClient) {
        super(webClient, BookingDto.class);
    }


    public Flux<BookingDto> getAllByBooker(Long userId, String state) {
        return getFluxWithHeaderAndParams(PATH, userId, Map.of("state", state));
    }

    public Flux<BookingDto> getAllByOwner(Long userId, String state) {
        return getFluxWithHeaderAndParams(PATH + "/owner", userId, Map.of("state", state));
    }

    public Mono<BookingDto> getByIdAndUserId(Long id, Long userId) {
        return getMonoWithHeader(PATH_WITH_VARS, userId, id);
    }

    public Mono<BookingDto> postBooking(BookingRequestDto dto) {
        return post(PATH, dto);
    }

    public Mono<BookingDto> patchBooking(BookingRequestDto dto) {
        return patch(PATH_WITH_VARS, dto, dto.getId());
    }
}
