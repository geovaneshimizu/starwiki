package io.geovaneshimizu.starwiki.character.swapi;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.JsonNode;

import io.geovaneshimizu.starwiki.character.ResourceId;
import io.geovaneshimizu.starwiki.character.swapi.SwApiResourceLink.ResourceIdExtractor;

class SwApiFilmCharacterIds implements Iterable<ResourceId> {

    private final Set<ResourceId> characterIds;

    private SwApiFilmCharacterIds(Collection<ResourceId> characterIds) {
        Objects.requireNonNull(characterIds);
        this.characterIds = new HashSet<>(characterIds);
    }

    static SwApiFilmCharacterIds fromFilmResource(JsonNode filmResource) {
        Objects.requireNonNull(filmResource);

        Iterable<JsonNode> characterLinks = () -> filmResource.path("characters").elements();

        List<ResourceId> characterIds =
                StreamSupport.stream(characterLinks.spliterator(), false)
                        .map(JsonNode::asText)
                        .map(SwApiResourceLink::of)
                        .map(characterLink -> characterLink.extractPartWith(new ResourceIdExtractor()))
                        .collect(Collectors.toList());

        return new SwApiFilmCharacterIds(characterIds);
    }

    @Override
    public Iterator<ResourceId> iterator() {
        return characterIds.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SwApiFilmCharacterIds that = (SwApiFilmCharacterIds) o;
        return Objects.equals(characterIds, that.characterIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterIds);
    }

    @Override
    public String toString() {
        return "SwApiFilmCharacterIds{" +
                "characterIds=" + characterIds +
                '}';
    }
}
