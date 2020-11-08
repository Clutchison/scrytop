package com.hutchison.scrytop.model.card.entity;

import com.hutchison.scrytop.model.card.enums.CardImgType;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

@Entity
@Table(name = "CARD_IMAGE")
@Builder
public class CardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "card_image_id")
    Long cardImageId;

    @ManyToOne
    private Card card;

    @Column(name = "image_type")
    CardImgType type;

    @Column(name = "image_content")
    byte[] content;
}
