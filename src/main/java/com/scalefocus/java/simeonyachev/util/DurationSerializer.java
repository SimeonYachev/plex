package com.scalefocus.java.simeonyachev.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class DurationSerializer extends JsonSerializer<Integer> {

    private static final int HOUR_IN_MILLISECONDS = 3_600_000;

    @Override
    public void serialize(Integer duration, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration - hours * HOUR_IN_MILLISECONDS);
        jsonGenerator.writeObject(String.format("%dh %dm", hours, minutes));
    }
}
