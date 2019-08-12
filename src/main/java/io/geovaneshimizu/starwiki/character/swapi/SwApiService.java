package io.geovaneshimizu.starwiki.character.swapi;

import java.util.function.BiPredicate;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.geovaneshimizu.starwiki.character.Character;
import io.geovaneshimizu.starwiki.character.CharactersFromTheSameSpeciesFilter;
import io.geovaneshimizu.starwiki.character.ListCharacters;
import io.geovaneshimizu.starwiki.character.ResourceId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
class SwApiService implements ListCharacters {

    private final SwApi swApi;

    SwApiService(SwApi swApi) {
        this.swApi = swApi;
    }

    @Override
    public Flux<Character> fromSameSpeciesByFilmIdAndCharacterId(ResourceId filmId,
                                                                 ResourceId characterId) {
        SwApiCharacterAdapter swApiCharacterAdapter = new SwApiCharacterAdapter();

        Mono<Character> referenceCharacterApiCall =
                swApi.getCharacterById(characterId)
                        .map(swApiCharacterAdapter)
                        .subscribeOn(Schedulers.elastic());

        Mono<SwApiFilmCharacterIds> filmCharacterIdsApiCall =
                swApi.getFilmById(filmId)
                        .map(SwApiFilmCharacterIds::fromFilmResource)
                        .subscribeOn(Schedulers.elastic());

        BiPredicate<Character, Character> areCharactersFromTheSameSpecies = new CharactersFromTheSameSpeciesFilter();

        return Mono.zip(
                referenceCharacterApiCall,
                filmCharacterIdsApiCall,
                (referenceCharacter, filmCharacterIds) ->
                        Flux.fromIterable(filmCharacterIds)
                                .flatMap(swApi::getCharacterById)
                                .map(swApiCharacterAdapter)
                                .filter(character -> areCharactersFromTheSameSpecies.test(character, referenceCharacter))
                                .parallel()
                                .runOn(Schedulers.elastic())
                                .sequential())
                .flatMapMany(Function.identity());
    }
}
