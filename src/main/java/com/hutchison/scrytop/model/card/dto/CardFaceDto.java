package com.hutchison.scrytop.model.card.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hutchison.scrytop.model.card.enums.Color;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CardFaceDto {
    String artist;
    @JsonAlias("color_indicator")
    Set<Color> colorIndicator;
    Set<Color> colors;
    @JsonAlias("flavor_text")
    String flavorText;
    @JsonAlias("illustration_id")
    UUID illustrationId;
    @JsonAlias("image_uris")
    ImageUrisDto imageURIsDto;
    String loyalty;
    @NonNull
    @JsonAlias("mana_cost")
    String manaCost;
    @NonNull
    String name;
    @JsonAlias("oracle_text")
    String oracleText;
    String power;
    @JsonAlias("printed_name")
    String printedName;
    @JsonAlias("printed_text")
    String printedText;
    @JsonAlias("printed_type_line")
    String printedTypeLine;
    String toughness;
    @NonNull
    @JsonAlias("type_line")
    String typeLine;
    String watermark;
}
