package com.application.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class StringToFloatDeserializer extends JsonDeserializer<Float> {



    @Override
    public Float deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            throw new IOException("Failed to parse float from JSON string: " + value, e);
        }
    }

}
