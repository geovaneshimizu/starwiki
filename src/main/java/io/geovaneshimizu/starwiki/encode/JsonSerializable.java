package io.geovaneshimizu.starwiki.encode;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

public interface JsonSerializable {

    void writeJson(JsonGenerator jsonGenerator) throws IOException;
}
