package io.geovaneshimizu.starwiki.character;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/jdtest")
class CharacterController {

    private final ListCharacters listCharacters;

    CharacterController(ListCharacters listCharacters) {
        this.listCharacters = listCharacters;
    }

    @GetMapping(params = {"film_id", "character_id"})
    Flux<CharacterName> getCharacterNamesFromSameSpeciesByFilmIdAndCharacterId(@RequestParam("film_id") String filmId,
                                                                               @RequestParam("character_id") String characterId) {
        return listCharacters.fromSameSpeciesByFilmIdAndCharacterId(ResourceId.of(filmId), ResourceId.of(characterId))
                .map(Character::name)
                .sort();
    }
}
