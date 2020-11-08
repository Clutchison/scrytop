package com.hutchison.scrytop.model.card.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Legality {
    LEGAL,
    NOT_LEGAL,
    RESTRICTED,
    BANNED;

    @JsonValue
    public String getJsonValue() {
        return this.name().toLowerCase();
    }
}
