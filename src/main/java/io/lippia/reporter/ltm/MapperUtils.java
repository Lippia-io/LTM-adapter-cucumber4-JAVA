package io.lippia.reporter.ltm;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperUtils {
    private MapperUtils() {
    }

    private static final ObjectMapper defaultMapper;

    static {
        defaultMapper = new ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
    }

    public static ObjectMapper getDefaultMapper() {
        return defaultMapper;
    }
}