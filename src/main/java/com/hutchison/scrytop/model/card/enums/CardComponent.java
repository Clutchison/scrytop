package com.hutchison.scrytop.model.card.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CardComponent {
    TOKEN,
    MELD_PART,
    MELD_RESULT,
    COMBO_PIECE;

    @JsonValue
    public String getJsonValue() {
        return this.name().toLowerCase();
    }
}
