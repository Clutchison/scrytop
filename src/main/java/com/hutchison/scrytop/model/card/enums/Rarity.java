package com.hutchison.scrytop.model.card.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Rarity {
    COMMON,
    UNCOMMON,
    RARE,
    MYTHIC;

    @JsonValue
    public String getJsonValue() {
        return this.name().toLowerCase();
    }
}
