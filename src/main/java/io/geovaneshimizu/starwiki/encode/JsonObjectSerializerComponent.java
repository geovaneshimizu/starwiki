package io.geovaneshimizu.starwiki.encode;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class JsonObjectSerializerComponent extends JsonObjectSerializer<JsonObjectSerializable> {

    @Override
    protected void serializeObject(JsonObjectSerializable serializable,
                                   JsonGenerator jsonGenerator,
                                   SerializerProvider provider) throws IOException {
        serializable.writeJson(jsonGenerator);
    }
}
