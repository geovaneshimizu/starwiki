package io.geovaneshimizu.starwiki.character.swapi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("starwiki.repository.swapi")
class SwApiProperties {

    private String baseUrl;

    private SwApiProperties() {
        // Properties holder
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
