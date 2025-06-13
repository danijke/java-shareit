package ru.practicum.shareit.booking.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.client.BaseWebClient;

@Component
public class BookingClient extends BaseWebClient<BookingDto> {

    public BookingClient(WebClient webClient, Class<BookingDto> responseType) {
        super(webClient, responseType);
    }


    public Flux<BookingDto> getAllByBooker(Long userId, String state) {
        return getFluxWithParams()
    }
}
