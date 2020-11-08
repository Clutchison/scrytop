package com.hutchison.scrytop.model.card.entity;

import com.hutchison.scrytop.model.card.dto.CardDto;
import com.hutchison.scrytop.model.card.dto.ScryfallCardDto;
import com.hutchison.scrytop.model.card.enums.BorderColor;
import com.hutchison.scrytop.model.card.enums.ColorCombo;
import com.hutchison.scrytop.model.card.enums.Frame;
import com.hutchison.scrytop.model.card.enums.FrameEffect;
import com.hutchison.scrytop.model.card.enums.Game;
import com.hutchison.scrytop.model.card.enums.Layout;
import com.hutchison.scrytop.model.card.enums.Rarity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.net.URI;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

@Entity(name = "card")
@Table(name = "card")
@Builder(toBuilder = true)
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "card_id")
    Long cardId;

    @Column(name = "uuid", unique = true, nullable = false)
    UUID uuid;
    @Column(name = "lang", nullable = false)
    String lang;
    @Column(name = "oracle_id", unique = true, nullable = false)
    UUID oracleId;
    @Column(name = "prints_search_uri", nullable = false)
    URI printsSearchUri;
    @Column(name = "rulings_uri", nullable = false)
    URI rulingsUri;
    @Column(name = "scryfall_uri", nullable = false)
    URI scryfallUri;
    @Column(name = "uri", nullable = false)
    URI uri;
    //    Gameplay Fields
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "card_related_card",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "related_card_id"))
    Set<RelatedCard> allParts; // todo
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "card_id")
    Set<CardFace> cardFaces;
    @Column(name = "cmc", nullable = false)
    Float cmc;
    @Column(name = "color_combo")
    ColorCombo colors;
    @Column(name = "color_identity")
    ColorCombo colorIdentity;
    @Column(name = "color_indicator")
    ColorCombo colorIndicator;
    @Column(name = "foil", nullable = false)
    boolean foil;
    @Column(name = "hand_modifier")
    String handModifier;
    @Column(name = "layout", nullable = false)
    Layout layout;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "legalities", referencedColumnName = "card_legalities_id")
    CardLegalities legalities;
    @Column(name = "life_monitor")
    String lifeMonitor;
    @Column(name = "loyalty")
    String loyalty;
    @Column(name = "mana_cost")
    String manaCost;
    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "non_foil", nullable = false)
    boolean nonFoil;
    @Column(name = "oracle_text")
    @Length(max = 775)
    String oracleText;
    @Column(name = "oversized", nullable = false)
    boolean oversized;
    @Column(name = "power")
    String power;
    @Column(name = "reserved", nullable = false)
    boolean reserved;
    @Column(name = "toughness")
    String toughness;
    @Column(name = "type_line", nullable = false)
    String typeLine;
    //    Print Fields
    @Column(name = "artist")
    String artist;
    @Column(name = "booster", nullable = false)
    boolean booster;
    @Column(name = "border_color", nullable = false)
    BorderColor borderColor;
    @Column(name = "card_back_id", nullable = false)
    UUID cardBackId;
    @Column(name = "collector_number", nullable = false)
    String collectorNumber;
    @Column(name = "digital", nullable = false)
    Boolean digital;
    @Column(name = "flavor_text")
    @Length(max = 410)
    String flavorText;
    @ElementCollection
    @CollectionTable(name = "card_frame_effect", joinColumns = @JoinColumn(name = "card_id"))
    Set<FrameEffect> frameEffects;
    @Column(name = "frame", nullable = false)
    Frame frame;
    @Column(name = "full_art", nullable = false)
    boolean fullArt;
    @ElementCollection
    @CollectionTable(name = "card_game", joinColumns = @JoinColumn(name = "card_id"))
    Set<Game> games;
    @Column(name = "highres_image", nullable = false)
    boolean highresImage;
    @Column(name = "illustration_id")
    UUID illustrationId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_image_uris", referencedColumnName = "card_image_uris_id")
    CardImageUris imageURIs;
    // prices
    @Column(name = "printed_name")
    String printedName;
    @Column(name = "printed_text")
    String printedText;
    @Column(name = "printed_type_line")
    String printedTypeLine;
    @Column(name = "promo", nullable = false)
    boolean promo;
    // todo  promo_types
    // todo purchase_uris
    @Column(name = "rarity", nullable = false)
    Rarity rarity;
    // todo related_uris
    @Column(name = "released_at", nullable = false)
    Date releasedAt;
    @Column(name = "reprint", nullable = false)
    boolean reprint;
    @Column(name = "scryfall_set_uri", nullable = false)
    URI scryfallSetURI;
    @Column(name = "set_name", nullable = false)
    String setName;
    @Column(name = "set_search_uri", nullable = false)
    URI setSearchURI;
    @Column(name = "set_type", nullable = false)
    String setType;
    @Column(name = "set_uri", nullable = false)
    URI setURI;
    @Column(name = "set", nullable = false)
    String set;
    @Column(name = "story_spotlight", nullable = false)
    boolean storySpotlight;
    @Column(name = "textless", nullable = false)
    boolean textless;
    @Column(name = "variation", nullable = false)
    boolean variation;
    @Column(name = "variation_of")
    UUID variationOf;
    @Column(name = "watermark")
    String watermark;
    @Column(name = "isCommonRemoval")
    boolean isCommonRemoval;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "card_id")
    Set<CardImage> images;
    // todo preview.previewed_at
    // todo preview.source_uri
    // todo preview.source

    public static Card fromDto(ScryfallCardDto dto, Set<RelatedCard> relatedCards) {
        return Card.builder()
                .uuid(dto.getUuid())
                .lang(dto.getLang())
                .oracleId(dto.getOracleId())
                .printsSearchUri(dto.getPrintsSearchUri())
                .rulingsUri(dto.getRulingsUri())
                .scryfallUri(dto.getScryfallUri())
                .uri(dto.getUri())
                .cmc(dto.getCmc())
                .foil(dto.isFoil())
                .handModifier(dto.getHandModifier())
                .layout(dto.getLayout())
                .lifeMonitor(dto.getLifeMonitor())
                .loyalty(dto.getLoyalty())
                .manaCost(dto.getManaCost())
                .name(dto.getName())
                .nonFoil(dto.isNonFoil())
                .oracleText(dto.getOracleText())
                .oversized(dto.isOversized())
                .power(dto.getPower())
                .reserved(dto.isReserved())
                .toughness(dto.getToughness())
                .typeLine(dto.getTypeLine())
                .artist(dto.getArtist())
                .booster(dto.isBooster())
                .borderColor(dto.getBorderColor())
                .cardBackId(dto.getCardBackId())
                .collectorNumber(dto.getCollectorNumber())
                .digital(dto.getDigital())
                .flavorText(dto.getFlavorText())
                .frame(dto.getFrame())
                .fullArt(dto.isFullArt())
                .highresImage(dto.isHighresImage())
                .illustrationId(dto.getIllustrationId())
                .printedName(dto.getPrintedName())
                .printedText(dto.getPrintedText())
                .printedTypeLine(dto.getPrintedTypeLine())
                .promo(dto.isPromo())
                .rarity(dto.getRarity())
                .releasedAt(dto.getReleasedAt())
                .reprint(dto.isReprint())
                .scryfallSetURI(dto.getScryfallSetURI())
                .setName(dto.getSetName())
                .setSearchURI(dto.getSetSearchURI())
                .setType(dto.getSetType())
                .setURI(dto.getSetURI())
                .set(dto.getSet())
                .storySpotlight(dto.isStorySpotlight())
                .textless(dto.isTextless())
                .variation(dto.isVariation())
                .variationOf(dto.getVariationOf())
                .watermark(dto.getWatermark())
                .colors(ColorCombo.fromColors(dto.getColors()))
                .colorIndicator(ColorCombo.fromColors(dto.getColorIndicator()))
                .legalities(CardLegalities.fromDto(dto.getLegalities()))
                .frameEffects(dto.getFrameEffects())
                .imageURIs(CardImageUris.fromDto(dto.getImageURIsDto()))
                .games(dto.getGames())
                .colorIdentity(ColorCombo.fromColors(dto.getColorIdentity()))
                .cardFaces(dto.getCardFaceDtos() == null ? null :
                        dto.getCardFaceDtos().stream()
                                .map(CardFace::fromDto)
                                .collect(Collectors.toSet()))
                .allParts(relatedCards)
                .isCommonRemoval(false) // todo: Set common removal properly
                .build();
    }

    public static Card fromDto(ScryfallCardDto dto) {
        return fromDto(dto, null);
    }

    public CardDto toDto() {
        return CardDto.builder()
                .uuid(getUuid())
                .lang(getLang())
                .oracleId(getOracleId())
                .printsSearchUri(getPrintsSearchUri())
                .rulingsUri(getRulingsUri())
                .scryfallUri(getScryfallUri())
                .uri(getUri())
                .cmc(getCmc())
                .foil(isFoil())
                .handModifier(getHandModifier())
                .layout(getLayout())
                .lifeMonitor(getLifeMonitor())
                .loyalty(getLoyalty())
                .manaCost(getManaCost())
                .name(getName())
                .nonFoil(isNonFoil())
                .oracleText(getOracleText())
                .oversized(isOversized())
                .power(getPower())
                .reserved(isReserved())
                .toughness(getToughness())
                .typeLine(getTypeLine())
                .artist(getArtist())
                .booster(isBooster())
                .borderColor(getBorderColor())
                .cardBackId(getCardBackId())
                .collectorNumber(getCollectorNumber())
                .digital(getDigital())
                .flavorText(getFlavorText())
                .frame(getFrame())
                .fullArt(isFullArt())
                .highresImage(isHighresImage())
                .illustrationId(getIllustrationId())
                .printedName(getPrintedName())
                .printedText(getPrintedText())
                .printedTypeLine(getPrintedTypeLine())
                .promo(isPromo())
                .rarity(getRarity())
                .releasedAt(getReleasedAt())
                .reprint(isReprint())
                .scryfallSetURI(getScryfallSetURI())
                .setName(getSetName())
                .setSearchURI(getSetSearchURI())
                .setType(getSetType())
                .setURI(getSetURI())
                .set(getSet())
                .storySpotlight(isStorySpotlight())
                .textless(isTextless())
                .variation(isVariation())
                .variationOf(getVariationOf())
                .watermark(getWatermark())
                .colors(colors.getColors())
                .colorIndicator(colorIndicator.getColors())
                .legalities((getLegalities().toDto()))
                .frameEffects(getFrameEffects())
                .imageURIsDto(getImageURIs().toDto())
                .games(getGames())
                .colorIdentity(colorIdentity.getColors())
                .cardFaceDtos(getCardFaces() == null ? null :
                        getCardFaces().stream()
                                .map(CardFace::toDto)
                                .collect(Collectors.toSet()))
                .allParts(getAllParts().stream()
                        .map(RelatedCard::toDto)
                        .collect(Collectors.toSet()))
                .build();
    }
}