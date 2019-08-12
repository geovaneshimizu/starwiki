package io.geovaneshimizu.starwiki.character;

import java.util.Comparator;
import java.util.Objects;

public class Character {

    private final ResourceId id;

    private final CharacterName name;

    private final ResourceId speciesId;

    public Character(ResourceId id,
                     CharacterName name,
                     ResourceId speciesId) {
        Objects.requireNonNull(id, "Character id must not be null");
        Objects.requireNonNull(name, "Character name must not be null");
        Objects.requireNonNull(speciesId, "Character speciesId must not be null");
        this.id = id;
        this.name = name;
        this.speciesId = speciesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return Objects.equals(id, character.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name=" + name +
                ", speciesId=" + speciesId +
                '}';
    }

    CharacterName name() {
        return name;
    }

    public enum CharacterComparator implements Comparator<Character> {

        BY_SPECIES {

            @Override
            public int compare(Character c1, Character c2) {
                return c1.speciesId.compareTo(c2.speciesId);
            }
        }
    }
}
