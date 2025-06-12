package ru.practicum.shareit.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.*;

@RequiredArgsConstructor
public abstract class BaseWebClient {

    protected final WebClient webClient;

    protected <T> Mono<T> get(String uri, Class<T> responseType, Object... uriVariables) {
        return webClient.get()
                .uri(uri, uriVariables)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleServerError)
                .bodyToMono(responseType);
    }

    protected <T> Flux<T> getFlux(String uri, Class<T> responseType, Object... uriVariables) {
        return webClient.get()
                .uri(uri, uriVariables)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleServerError)
                .bodyToFlux(responseType);
    }

    protected <T, R> Mono<R> post(String uri, T body, Class<R> responseType) {
        return webClient.post()
                .uri(uri)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleServerError)
                .bodyToMono(responseType);
    }

    protected <T, R> Mono<R> patch(String uri, T body, Class<R> responseType, Object... uriVariables) {
        return webClient.patch()
                .uri(uri, uriVariables)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleServerError)
                .bodyToMono(responseType);
    }

    protected Mono<Void> delete(String uri, Object... uriVariables) {
        return webClient.delete()
                .uri(uri, uriVariables)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleServerError)
                .bodyToMono(Void.class);
    }

    private Mono<? extends Throwable> handleServerError(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new RuntimeException(body)));
    }
}

