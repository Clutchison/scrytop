package com.hutchison.scrytop.model.card.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Layout {
    NORMAL,
    SPLIT,
    FLIP,
    TRANSFORM,
    MELD,
    LEVELER,
    SAGA,
    ADVENTURE,
    PLANAR,
    SCHEME,
    VANGUARD,
    TOKEN,
    DOUBLE_FACED_TOKEN,
    EMBLEM,
    AUGMENT,
    HOST,
    ART_SERIES,
    DOUBLE_SIDED,
    MODAL_DFC;

    @JsonValue
    public String getJsonValue() {
        return this.name().toLowerCase();
    }


}
