package com.hutchison.scrytop.model.card.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BorderColor {
    BLACK,
    BORDERLESS,
    GOLD,
    SILVER,
    WHITE;

    @JsonValue
    public String getJsonValue() {
        return this.name().toLowerCase();
    }
}
