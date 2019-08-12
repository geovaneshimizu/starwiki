package io.geovaneshimizu.starwiki.character.swapi;

import java.util.Objects;
import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;

import io.geovaneshimizu.starwiki.character.Character;
import io.geovaneshimizu.starwiki.character.CharacterName;
import io.geovaneshimizu.starwiki.character.ResourceId;
import io.geovaneshimizu.starwiki.character.UnknownResourceId;
import io.geovaneshimizu.starwiki.character.swapi.SwApiResourceLink.ResourceIdExtractor;

class SwApiCharacterAdapter implements Function<JsonNode, Character> {

    @Override
    public Character apply(JsonNode characterResource) {
        Objects.requireNonNull(characterResource);

        ResourceId characterId =
                SwApiResourceLink.of(characterResource.path("url").asText())
                        .extractPartWith(new ResourceIdExtractor());

        CharacterName characterName = CharacterName.of(characterResource.path("name").asText());

        ResourceId characterSpeciesId;

        JsonNode speciesLink = characterResource.path("species").path(0);
        if (speciesLink.isMissingNode()) {
            characterSpeciesId = new UnknownResourceId();
        } else {
            characterSpeciesId =
                    SwApiResourceLink.of(speciesLink.asText())
                            .extractPartWith(new ResourceIdExtractor());
        }

        return new Character(characterId, characterName, characterSpeciesId);
    }
}
