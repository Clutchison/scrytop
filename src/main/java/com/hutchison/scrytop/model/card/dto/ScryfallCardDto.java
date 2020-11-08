package com.hutchison.scrytop.model.card.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hutchison.scrytop.model.card.enums.BorderColor;
import com.hutchison.scrytop.model.card.enums.Color;
import com.hutchison.scrytop.model.card.enums.Frame;
import com.hutchison.scrytop.model.card.enums.FrameEffect;
import com.hutchison.scrytop.model.card.enums.Game;
import com.hutchison.scrytop.model.card.enums.Layout;
import com.hutchison.scrytop.model.card.enums.Rarity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.net.URI;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScryfallCardDto {
    //    Core Fields
    @NonNull
    @JsonAlias("id")
    UUID uuid;
    @NonNull
    String lang;
    @NonNull
    @JsonAlias("oracle_id")
    UUID oracleId;
    @NonNull
    @JsonAlias("prints_search_uri")
    URI printsSearchUri;
    @NonNull
    @JsonAlias("rulings_uri")
    URI rulingsUri;
    @NonNull
    @JsonAlias("scryfall_uri")
    URI scryfallUri;
    @NonNull
    URI uri;
    //    Gameplay Fields
    @JsonAlias("all_parts")
    Set<RelatedCardDto> allParts;
    @JsonAlias("card_faces")
    Set<CardFaceDto> cardFaceDtos;
    @NonNull
    Float cmc;
    Set<Color> colors;
    @JsonAlias("color_identity")
    Set<Color> colorIdentity;
    @JsonAlias("color_indicator")
    Set<Color> colorIndicator;
    @NonNull
    boolean foil;
    @JsonAlias("hand_modifier")
    String handModifier;
    @NonNull
    Layout layout;
    CardLegalitiesDto legalities;
    @JsonAlias("life_monitor")
    String lifeMonitor;
    String loyalty;
    @JsonAlias("mana_cost")
    String manaCost;
    @NonNull
    String name;
    @NonNull
    boolean nonFoil;
    @JsonAlias("oracle_text")
    String oracleText;
    @NonNull
    boolean oversized;
    String power;
    @NonNull
    boolean reserved;
    String toughness;
    @NonNull
    @JsonAlias("type_line")
    String typeLine;
    //    Print Fields
    String artist;
    @NonNull
    boolean booster;
    @NonNull
    @JsonAlias("border_color")
    BorderColor borderColor;
    @NonNull
    @JsonAlias("card_back_id")
    UUID cardBackId;
    @NonNull
    @JsonAlias("collector_number")
    String collectorNumber;
    @NonNull
    Boolean digital;
    @JsonAlias("flavor_text")
    String flavorText;
    @JsonAlias("frame_effects")
    Set<FrameEffect> frameEffects;
    @NonNull
    Frame frame;
    @NonNull
    @JsonAlias("full_art")
    boolean fullArt;
    @NonNull
    Set<Game> games;
    @NonNull
    @JsonAlias("highres_image")
    boolean highresImage;
    @JsonAlias("illustration_id")
    UUID illustrationId;
    @JsonAlias("image_uris")
    ImageUrisDto imageURIsDto;
    // prices
    @JsonAlias("printed_name")
    String printedName;
    @JsonAlias("printed_text")
    String printedText;
    @JsonAlias("printed_type_line")
    String printedTypeLine;
    @NonNull
    boolean promo;
    // promo_types
    // purchase_uris
    @NonNull
    Rarity rarity;
    // related_uris
    @NonNull
    @JsonAlias("released_at")
    Date releasedAt;
    @NonNull
    boolean reprint;
    @NonNull
    @JsonAlias("scryfall_set_uri")
    URI scryfallSetURI;
    @NonNull
    @JsonAlias("set_name")
    String setName;
    @NonNull
    @JsonAlias("set_search_uri")
    URI setSearchURI;
    @NonNull
    @JsonAlias("set_type")
    String setType;
    @NonNull
    @JsonAlias("set_uri")
    URI setURI;
    @NonNull
    String set;
    @NonNull
    @JsonAlias("story_spotlight")
    boolean storySpotlight;
    @NonNull
    boolean textless;
    @NonNull
    boolean variation;
    @JsonAlias("variation_of")
    UUID variationOf;
    String watermark;
    // preview.previewed_at
    // preview.source_uri
    // preview.source
}
