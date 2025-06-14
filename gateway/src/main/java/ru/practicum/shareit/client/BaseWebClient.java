package ru.practicum.shareit.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.*;
import ru.practicum.shareit.error.ErrorResponse;
import ru.practicum.shareit.exception.ForwardedServerException;

import java.util.Map;

@RequiredArgsConstructor
public abstract class BaseWebClient<R> {

    protected final WebClient webClient;

    private final Class<R> responseType;

    protected Mono<R> get(String uri, Object... uriVariables) {
        return webClient.get()
                .uri(uri, uriVariables)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleServerError)
                .bodyToMono(responseType);
    }

    protected Mono<R> getMonoWithHeader(String uri, Long userId, Object... uriVariables) {
        return webClient.get()
                .uri(uri, uriVariables)
                .header("X-Sharer-User-Id", String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleServerError)
                .bodyToMono(responseType);
    }

    protected Flux<R> getFlux(String uri, Object... uriVariables) {
        return webClient.get()
                .uri(uri, uriVariables)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleServerError)
                .bodyToFlux(responseType);
    }

    protected Flux<R> getFluxWithHeader(String uri, Long userId, Object... uriVariables) {
        return webClient.get()
                .uri(uri, uriVariables)
                .header("X-Sharer-User-Id", String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleServerError)
                .bodyToFlux(responseType);
    }

    protected Flux<R> getFluxWithParams(String uri, Map<String, String> queryParams) {
        return webClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder.path(uri);
                    queryParams.forEach(builder::queryParam);
                    return builder.build();
                })
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleServerError)
                .bodyToFlux(responseType);
    }

    protected Flux<R> getFluxWithHeaderAndParams(String uri, Long userId, Map<String, String> queryParams) {
        return webClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder.path(uri);
                    queryParams.forEach(builder::queryParam);
                    return builder.build();
                })
                .header("X-Sharer-User-Id", String.valueOf(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleServerError)
                .bodyToFlux(responseType);
    }

    protected <T, Type> Mono<Type> post(String uri, T body, Class<Type> type, Object... uriVariables) {
        return webClient.post()
                .uri(uri, uriVariables)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleServerError)
                .bodyToMono(type);
    }

    protected <T> Mono<R> post(String uri, T body, Object... uriVariables) {
        return webClient.post()
                .uri(uri, uriVariables)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleServerError)
                .bodyToMono(responseType);
    }

    protected <T> Mono<R> patch(String uri, T body, Object... uriVariables) {
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
        return response.bodyToMono(ErrorResponse.class)
                .flatMap(error -> Mono.error(new ForwardedServerException(error)));
    }
}

