package com.hutchison.scrytop.model.card.entity;

import com.hutchison.scrytop.model.card.dto.CardFaceDto;
import com.hutchison.scrytop.model.card.enums.Color;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

@Entity
@Table(name = "CARD_FACE")
@Builder
public class CardFace {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "id")
    Long cardFaceId;

    @ManyToOne
    Card card;

    @Column(name = "artist")
    String artist;
    @ElementCollection
    @CollectionTable(name = "card_face_indicator_color",
            joinColumns = @JoinColumn(name = "card_face_id"))
    Set<Color> colorIndicator;
    @ElementCollection
    @CollectionTable(name = "card_face_color",
            joinColumns = @JoinColumn(name = "card_face_id"))
    Set<Color> colors;
    @Column(name = "flavorText")
    String flavorText;
    @Column(name = "illustrationId")
    UUID illustrationId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_face_image_uris_id", referencedColumnName = "card_image_uris_id")
    CardImageUris imagesURIs;
    @Column(name = "loyalty")
    String loyalty;
    @Column(name = "mana_cost", nullable = false)
    String manaCost;
    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "oracle_text", nullable = false)
    @Length(max = 400)
    String oracleText;
    @Column(name = "power")
    String power;
    @Column(name = "printed_name")
    String printedName;
    @Column(name = "printed_text")
    String printedText;
    @Column(name = "printed_type_line")
    String printedTypeLine;
    @Column(name = "toughness")
    String toughness;
    @Column(name = "type_line")
    String typeLine;
    @Column(name = "watermark")
    String watermark;

    public static CardFace fromDto(CardFaceDto dto) {
        return CardFace.builder()
                .artist(dto.getArtist())
                .colorIndicator(dto.getColorIndicator())
                .colors(dto.getColors())
                .flavorText(dto.getFlavorText())
                .illustrationId(dto.getIllustrationId())
                .imagesURIs(CardImageUris.fromDto(dto.getImageURIsDto()))
                .loyalty(dto.getLoyalty())
                .manaCost(dto.getManaCost())
                .name(dto.getName())
                .oracleText(dto.getOracleText())
                .power(dto.getPower())
                .printedName(dto.getPrintedName())
                .printedText(dto.getPrintedText())
                .printedTypeLine(dto.getPrintedTypeLine())
                .toughness(dto.getToughness())
                .typeLine(dto.getTypeLine())
                .watermark(dto.getWatermark())
                .build();
    }

    public CardFaceDto toDto() {
        return CardFaceDto.builder()
                .artist(getArtist())
                .colorIndicator(getColorIndicator())
                .colors(getColors())
                .flavorText(getFlavorText())
                .illustrationId(getIllustrationId())
                .imageURIsDto(getImagesURIs().toDto())
                .loyalty(getLoyalty())
                .manaCost(getManaCost())
                .name(getName())
                .oracleText(getOracleText())
                .power(getPower())
                .printedName(getPrintedName())
                .printedText(getPrintedText())
                .printedTypeLine(getPrintedTypeLine())
                .toughness(getToughness())
                .typeLine(getTypeLine())
                .watermark(getWatermark())
                .build();
    }
}
