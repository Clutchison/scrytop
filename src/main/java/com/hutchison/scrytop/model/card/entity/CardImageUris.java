package com.hutchison.scrytop.model.card.entity;

import com.hutchison.scrytop.model.card.dto.ImageUrisDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.net.URI;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

@Entity
@Table(name = "CARD_IMAGE_URIS")
@Builder
public class CardImageUris {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "card_image_uris_id")
    Long cardImageUrisId;

    @OneToOne
    private Card card;

    @Column(name = "small")
    URI small;
    @Column(name = "normal")
    URI normal;
    @Column(name = "large")
    URI large;
    @Column(name = "png")
    URI png;
    @Column(name = "art_crop")
    URI artCrop;
    @Column(name = "border_crop")
    URI borderCrop;

    public static CardImageUris fromDto(ImageUrisDto dto) {
        if (dto == null) return null;
        return CardImageUris.builder()
                .small(dto.getSmall())
                .normal(dto.getNormal())
                .large(dto.getLarge())
                .png(dto.getPng())
                .artCrop(dto.getArtCrop())
                .borderCrop(dto.getBorderCrop())
                .build();
    }

    public ImageUrisDto toDto() {
        return ImageUrisDto.builder()
                .small(getSmall())
                .normal(getNormal())
                .large(getLarge())
                .png(getPng())
                .artCrop(getArtCrop())
                .borderCrop(getBorderCrop())
                .build();
    }
}
