package io.geovaneshimizu.starwiki.character;

import java.util.Objects;
import java.util.regex.Pattern;

public class ResourceId implements Comparable<ResourceId> {

    private static final Pattern RESOURCE_ID_PATTERN = Pattern.compile("^[1-9]\\d*$");

    private final String value;

    protected ResourceId() {
        this.value = "";
    }

    private ResourceId(String value) {
        Objects.requireNonNull(value, "ResourceId value must not be null");
        if (!RESOURCE_ID_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid ResourceId value: " + value);
        }
        this.value = value;
    }

    public static ResourceId of(String value) {
        return new ResourceId(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceId that = (ResourceId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(ResourceId other) {
        return this.value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return "ResourceId{" +
                "value='" + value + '\'' +
                '}';
    }

    public String asString() {
        return value;
    }
}
