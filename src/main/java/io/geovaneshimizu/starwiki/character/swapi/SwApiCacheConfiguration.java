package io.geovaneshimizu.starwiki.character.swapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import io.geovaneshimizu.starwiki.character.ResourceId;

@Configuration
class SwApiCacheConfiguration {

    @Bean
    Cache<ResourceId, Object> characterCache() {
        return Caffeine
                .newBuilder()
                .maximumSize(100L)
                .build();
    }

    @Bean
    Cache<ResourceId, Object> filmCache() {
        return Caffeine
                .newBuilder()
                .maximumSize(10L)
                .build();
    }
}
