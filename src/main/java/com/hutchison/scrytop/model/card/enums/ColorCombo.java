package com.hutchison.scrytop.model.card.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.hutchison.scrytop.model.card.enums.Color.*;

@Getter
public enum ColorCombo {
    C("Colorless", new HashSet<>()),
    W("White", Set.of(WHITE)),
    U("Blue", Set.of(BLUE)),
    B("Black", Set.of(BLACK)),
    R("Red", Set.of(RED)),
    G("Green", Set.of(GREEN)),
    WU("Azorius", Set.of(WHITE, BLUE)),
    WB("Orzhov", Set.of(WHITE, BLACK)),
    WR("Boros", Set.of(WHITE, RED)),
    WG("Selesnya", Set.of(WHITE, GREEN)),
    UB("Dimir", Set.of(BLUE, BLACK)),
    UR("Izzet", Set.of(BLUE, RED)),
    UG("Simic", Set.of(BLUE, GREEN)),
    BR("Rakdos", Set.of(BLACK, RED)),
    BG("Golgari", Set.of(BLACK, GREEN)),
    RG("Gruul", Set.of(RED, GREEN)),
    WUB("Esper", Set.of(WHITE, BLUE, BLACK)),
    WUR("Jeskai", Set.of(WHITE, BLUE, RED)),
    WUG("Bant", Set.of(WHITE, BLUE, GREEN)),
    WBR("Mardu", Set.of(WHITE, BLACK, RED)),
    WBG("Abzan", Set.of(WHITE, BLACK, GREEN)),
    WRG("Naya", Set.of(WHITE, RED, GREEN)),
    UBR("Grixis", Set.of(BLUE, BLACK, RED)),
    UBG("Sultai", Set.of(BLUE, BLACK, GREEN)),
    URG("Temur", Set.of(BLUE, RED, GREEN)),
    BRG("Jund", Set.of(BLACK, RED, GREEN)),
    UBRG("Chaos", Set.of(BLUE, BLACK, RED, GREEN)),
    BRGW("Aggression", Set.of(BLACK, RED, GREEN, WHITE)),
    RGWU("Altruism", Set.of(RED, GREEN, WHITE, BLUE)),
    WUBR("Growth", Set.of(WHITE, BLUE, BLACK, RED)),
    WUBRG("Artifice", Set.of(WHITE, BLUE, BLACK, RED, GREEN));

    private final String nickname;
    private final Set<Color> colors;
    private static Map<Set<Color>, ColorCombo> map = new HashMap<>();

    static {
        Arrays.stream(ColorCombo.values())
                .forEach(c -> map.put(c.getColors(), c));
    }

    ColorCombo(String nickname, Set<Color> colors) {
        this.nickname = nickname;
        this.colors = colors;
    }

    public static ColorCombo fromColors(Set<Color> colors) {
        return map.get(colors);
    }

    public boolean isColor(Color color) {
        return this.colors.contains(color);
    }
}
