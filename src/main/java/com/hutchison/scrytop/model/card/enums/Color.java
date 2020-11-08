package com.hutchison.scrytop.model.card.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Getter
public enum Color {
    WHITE('W'),
    BLUE('U'),
    BLACK('B'),
    RED('R'),
    GREEN('G');

    @JsonValue
    private final char character;
    private static Map<Character, Color> map;

    Color(char character) {
        this.character = character;
    }

    static {
        map = new HashMap();
        Stream.of(Color.values())
                .forEach(color -> map.put(color.getCharacter(), color));
    }

    public static Color fromCharacter(char colorChar) {
        Color color = map.get(colorChar);
        if (color == null) {
            throw new RuntimeException("No color for character: " + colorChar);
        } else {
            return color;
        }
    }
}
