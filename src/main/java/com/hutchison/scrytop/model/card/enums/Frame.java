package com.hutchison.scrytop.model.card.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Frame {
    F1993("1993"),
    F1997("1997"),
    F2003("2003"),
    F2015("2015"),
    FFUTURE("future");

    @JsonValue
    private final String json;

    Frame(String json) {
        this.json = json;
    }
}
