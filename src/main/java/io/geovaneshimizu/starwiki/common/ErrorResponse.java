package io.geovaneshimizu.starwiki.common;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonGenerator;

import io.geovaneshimizu.starwiki.encode.JsonObjectSerializable;

class ErrorResponse implements JsonObjectSerializable {

    private final Long timestamp;

    private final String path;

    private final Integer status;

    private final String error;

    private final String message;

    ErrorResponse(Long timestamp,
                  String path,
                  Integer status,
                  String error,
                  String message) {
        this.timestamp = timestamp;
        this.path = path;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(path, that.path) &&
                Objects.equals(status, that.status) &&
                Objects.equals(error, that.error) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, path, status, error, message);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "timestamp=" + timestamp +
                ", path='" + path + '\'' +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public void writeJson(JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStringField("timestamp", timestamp.toString());
        jsonGenerator.writeStringField("path", path);
        jsonGenerator.writeStringField("status", status.toString());
        jsonGenerator.writeStringField("error", error);
        jsonGenerator.writeStringField("message", message);
    }
}
