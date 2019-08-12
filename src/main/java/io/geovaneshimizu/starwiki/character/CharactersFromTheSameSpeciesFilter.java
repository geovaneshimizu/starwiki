package io.geovaneshimizu.starwiki.character;

import java.util.function.BiPredicate;

import io.geovaneshimizu.starwiki.character.Character.CharacterComparator;

public class CharactersFromTheSameSpeciesFilter implements BiPredicate<Character, Character> {

    @Override
    public boolean test(Character character1, Character character2) {
        return CharacterComparator.SAME_SPECIES.compare(character1, character2) == 0;
    }
}
