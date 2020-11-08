package com.hutchison.scrytop.model.card.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FrameEffect {
    LEGENDARY,
    MIRACLE,
    NYXTOUCHED,
    DRAFT,
    DEVOID,
    TOMBSTONE,
    COLORSHIFTED,
    INVERTED,
    SUNMOONDFC,
    COMPASSLANDDFC,
    ORIGINPWDFC,
    MOONELDRAZIDFC,
    MOONREVERSEDMOONDFC,
    WAXINGANDWANINGMOONDFC,
    SHOWCASE,
    EXTENDEDART,
    COMPANION;

    @JsonValue
    public String getJsonValue() {
        return this.name().toLowerCase();
    }
}
