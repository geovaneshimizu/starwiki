package io.geovaneshimizu.starwiki.character;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonGenerator;

import io.geovaneshimizu.starwiki.encode.JsonSerializable;

public class CharacterName implements JsonSerializable, Comparable<CharacterName> {

    private final String value;

    private CharacterName(String value) {
        Objects.requireNonNull(value, "CharacterName value must not be null");
        if (value.isEmpty()) {
            throw new IllegalArgumentException("CharacterName value must not be empty");
        }
        this.value = value;
    }

    public static CharacterName of(String value) {
        return new CharacterName(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterName that = (CharacterName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(CharacterName other) {
        return this.value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return "CharacterName{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public void writeJson(JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeString(value);
    }
}
