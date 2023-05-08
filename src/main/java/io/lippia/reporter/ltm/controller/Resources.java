package io.lippia.reporter.ltm.controller;

import io.lippia.reporter.ltm.TestManagerAPIAdapter;

import java.net.URL;

public final class Resources {
    public static URL getAsURL(String resource) {
        return TestManagerAPIAdapter.class.getClassLoader().getResource(resource);
    }

    public static String getAsPath(String resource) {
        return getAsURL(resource).getPath();
    }
}