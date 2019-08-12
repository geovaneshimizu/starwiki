package io.geovaneshimizu.starwiki.encode;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class JsonSerializerComponent extends JsonSerializer<JsonSerializable> {

    @Override
    public void serialize(JsonSerializable serializable,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        serializable.writeJson(jsonGenerator);
    }
}
