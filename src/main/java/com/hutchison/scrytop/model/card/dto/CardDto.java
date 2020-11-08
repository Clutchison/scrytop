package com.hutchison.scrytop.model.card.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hutchison.scrytop.model.card.enums.BorderColor;
import com.hutchison.scrytop.model.card.enums.Color;
import com.hutchison.scrytop.model.card.enums.Frame;
import com.hutchison.scrytop.model.card.enums.FrameEffect;
import com.hutchison.scrytop.model.card.enums.Game;
import com.hutchison.scrytop.model.card.enums.Layout;
import com.hutchison.scrytop.model.card.enums.Rarity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.net.URI;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CardDto {
    //    Core Fields
    UUID uuid;
    String lang;
    UUID oracleId;
    URI printsSearchUri;
    URI rulingsUri;
    URI scryfallUri;
    URI uri;
    //    Gameplay Fields
    Set<RelatedCardDto> allParts;
    Set<CardFaceDto> cardFaceDtos;
    Float cmc;
    Set<Color> colors;
    Set<Color> colorIdentity;
    Set<Color> colorIndicator;
    boolean foil;
    String handModifier;
    Layout layout;
    CardLegalitiesDto legalities;
    String lifeMonitor;
    String loyalty;
    String manaCost;
    String name;
    boolean nonFoil;
    String oracleText;
    boolean oversized;
    String power;
    boolean reserved;
    String toughness;
    String typeLine;
    //    Print Fields
    String artist;
    boolean booster;
    BorderColor borderColor;
    UUID cardBackId;
    String collectorNumber;
    Boolean digital;
    String flavorText;
    Set<FrameEffect> frameEffects;
    Frame frame;
    boolean fullArt;
    Set<Game> games;
    boolean highresImage;
    UUID illustrationId;
    ImageUrisDto imageURIsDto;
    // prices
    String printedName;
    String printedText;
    String printedTypeLine;
    boolean promo;
    // promo_types
    // purchase_uris
    Rarity rarity;
    // related_uris
    Date releasedAt;
    boolean reprint;
    URI scryfallSetURI;
    String setName;
    URI setSearchURI;
    String setType;
    URI setURI;
    String set;
    boolean storySpotlight;
    boolean textless;
    boolean variation;
    UUID variationOf;
    String watermark;
    // preview.previewed_at
    // preview.source_uri
    // preview.source
}
