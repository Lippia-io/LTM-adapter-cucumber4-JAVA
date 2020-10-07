package io.lippia.reporter.service;

import java.io.Serializable;

@FunctionalInterface
public interface Markup
extends Serializable {
    public String getMarkup();
}