package io.geovaneshimizu.starwiki.character.swapi;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.regex.Pattern;

import io.geovaneshimizu.starwiki.character.ResourceId;

class SwApiResourceLink {

    private final URL link;

    private SwApiResourceLink(String link) {
        Objects.requireNonNull(link, "Link value must not be null");
        try {
            this.link = new URL(link);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid link value: " + link, e);
        }
    }

    static SwApiResourceLink of(String link) {
        return new SwApiResourceLink(link);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SwApiResourceLink swApiResourceLink = (SwApiResourceLink) o;
        return Objects.equals(link, swApiResourceLink.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }

    @Override
    public String toString() {
        return "SwApiResourceLink{" +
                "link=" + link +
                '}';
    }

    <T> T extractPartWith(Function<SwApiResourceLink, T> extractor) {
        return extractor.apply(this);
    }

    static class ResourceIdExtractor implements Function<SwApiResourceLink, ResourceId> {

        @Override
        public ResourceId apply(SwApiResourceLink swApiResourceLink) {
            BinaryOperator<String> lastPathParameter = (first, second) -> second;

            return Pattern.compile("/")
                    .splitAsStream(swApiResourceLink.link.getPath())
                    .reduce(lastPathParameter)
                    .map(ResourceId::of)
                    .orElseThrow(() ->
                            new IllegalArgumentException(swApiResourceLink + " is not from a specific resource"));
        }
    }
}
