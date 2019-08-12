package io.geovaneshimizu.starwiki.character.swapi;

import java.time.Duration;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.benmanes.caffeine.cache.Cache;

import io.geovaneshimizu.starwiki.character.ResourceId;
import reactor.cache.CacheMono;
import reactor.core.publisher.Mono;

@Component
class SwApi {

    private final WebClient swApiWebClient;

    private final Cache<ResourceId, Object> characterCache;

    private final Cache<ResourceId, Object> filmCache;

    private final Logger logger;

    SwApi(WebClient swApiWebClient,
          Cache<ResourceId, Object> characterCache,
          Cache<ResourceId, Object> filmCache,
          Logger logger) {
        this.swApiWebClient = swApiWebClient;
        this.characterCache = characterCache;
        this.filmCache = filmCache;
        this.logger = logger;
    }

    Mono<JsonNode> getCharacterById(ResourceId characterId) {
        return CacheMono
                .lookup(characterCache.asMap(), characterId, JsonNode.class)
                .onCacheMissResume(() ->
                        swApiWebClient.get()
                                .uri(uriBuilder ->
                                        uriBuilder.path("/people/{personId}/")
                                                .build(characterId.asString()))
                                .retrieve()
                                .bodyToMono(JsonNode.class)
                                .retryBackoff(3, Duration.ofMillis(200L))
                                .onErrorResume(throwable -> {
                                    logger.warn(throwable.getMessage());
                                    return Mono.empty();
                                }));
    }

    Mono<JsonNode> getFilmById(ResourceId filmId) {
        return CacheMono
                .lookup(filmCache.asMap(), filmId, JsonNode.class)
                .onCacheMissResume(() ->
                        swApiWebClient.get()
                                .uri(uriBuilder ->
                                        uriBuilder.path("/films/{filmId}/")
                                                .build(filmId.asString()))
                                .retrieve()
                                .bodyToMono(JsonNode.class)
                                .retryBackoff(3, Duration.ofMillis(200L))
                                .onErrorResume(throwable -> {
                                    logger.warn(throwable.getMessage());
                                    return Mono.empty();
                                }));
    }
}
