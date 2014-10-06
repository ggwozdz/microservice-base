package com.acxiom.microservice.json;

import java.io.IOException;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValueObject {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String toString() {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (IOException e) {
            return super.toString();
        }
    }

    public String etag() {
        return Integer.toString(hashCode());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            return this.toString().equals(obj.toString());
        } else {
            return false;
        }
    }
}
