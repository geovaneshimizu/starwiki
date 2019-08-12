package io.geovaneshimizu.starwiki.character.swapi;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Configuration
class SwApiWebClientConfiguration {

    @Bean
    WebClientCustomizer webClientCustomizer() {
        return webClientBuilder -> webClientBuilder.clientConnector(reactorClientHttpConnector());
    }

    @Bean
    WebClient swApiWebClient(WebClient.Builder webClientBuilder,
                             SwApiProperties swApiProperties,
                             Logger logger) {
        return webClientBuilder
                .baseUrl(swApiProperties.getBaseUrl())
                .filter(logRequest(logger))
                .filter(logResponse(logger))
                .build();
    }

    private ReactorClientHttpConnector reactorClientHttpConnector() {
        return new ReactorClientHttpConnector(HttpClient.from(tcpClient()));
    }

    private TcpClient tcpClient() {
        return TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5 * 1000)
                .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(5)));
    }

    private ExchangeFilterFunction logRequest(Logger logger) {
        return ExchangeFilterFunction
                .ofRequestProcessor(clientRequest ->
                        Mono.just(clientRequest)
                                .doOnEach(logOnNext(request -> logger.debug("Request: {} {}", request.method(), request.url()))));
    }

    private ExchangeFilterFunction logResponse(Logger logger) {
        return ExchangeFilterFunction
                .ofResponseProcessor(clientResponse ->
                        Mono.just(clientResponse)
                                .doOnEach(logOnNext(response -> logger.debug("Response: {}", response.statusCode()))));
    }

    private <T> Consumer<Signal<T>> logOnNext(Consumer<T> logStatement) {
        return signal -> {
            if (!signal.isOnNext()) {
                return;
            }
            logStatement.accept(signal.get());
        };
    }
}
