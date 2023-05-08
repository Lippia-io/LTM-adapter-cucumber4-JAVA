package io.lippia.reporter.ltm.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.lippia.api.configuration.EndpointConfiguration;
import io.lippia.api.service.CallerService;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static io.lippia.api.service.MethodServiceEnum.NOSSLVERIFICATION;

public final class APIController {
    private APIController() {
    }

    public static <T> String sendRequest(String relativePath, T model) {
        requestConfigurer(deserialize(relativePath, model));
        try {
            return (String) CallerService.call(EndpointConfiguration.getInstance());
        } catch (IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static <T> JsonObject deserialize(String resource, T model) {
        URL path = Resources.getAsURL(resource);

        if (path == null) {
            throw new IllegalArgumentException("the classloader cannot be able to locate the resource " + resource);
        } else {
            try {
                String json = IOUtils.toString(path, StandardCharsets.UTF_8);
                // replace vars with model reference
                return new Gson().fromJson(json, JsonObject.class);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private static void requestConfigurer(JsonObject JSONObject) {
        EndpointConfiguration.clean();
        EndpointConfiguration.getInstance().setMethodService(NOSSLVERIFICATION);

        if (!JSONObject.has("method") || !JSONObject.has("url") ||
                !JSONObject.has("headers")) {
            throw new RuntimeException("http method, url, or headers properties must be defined");
        }

        if (JSONObject.has("method")) {
            EndpointConfiguration.method(JSONObject.get("method").getAsString());
        }

        if (JSONObject.has("url")) {
            EndpointConfiguration.url(JSONObject.get("url").getAsString());
        }

        if (JSONObject.has("endpoint")) {
            EndpointConfiguration.endpoint(JSONObject.get("endpoint").getAsString());
        }

        if (JSONObject.has("headers")) {
            JSONObject.get("headers").getAsJsonObject().entrySet().forEach(entry -> {
                EndpointConfiguration.header(entry.getKey(), entry.getValue().getAsString());
            });
        }

        if (JSONObject.has("body")) {
            JsonElement body = JSONObject.get("body");
            EndpointConfiguration.body(body instanceof JsonPrimitive ? body.getAsString() : body.toString());
        }
    }
}