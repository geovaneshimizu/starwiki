package io.geovaneshimizu.starwiki.character;

import reactor.core.publisher.Flux;

public interface ListCharacters {

    Flux<Character> fromSameSpeciesByFilmIdAndCharacterId(ResourceId filmId,
                                                          ResourceId characterId);
}
