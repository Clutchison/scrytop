package com.hutchison.scrytop.model.card.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Game {
    PAPER,
    ARENA,
    MTGO;

    @JsonValue
    public String getJsonValue() {
        return this.name().toLowerCase();
    }
}
