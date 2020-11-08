package com.hutchison.scrytop.model.card.entity;

import com.hutchison.scrytop.model.card.dto.CardLegalitiesDto;
import com.hutchison.scrytop.model.card.enums.Legality;
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

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

@Entity
@Table(name = "CARD_LEGALITIES")
@Builder
public class CardLegalities {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "card_legalities_id")
    Long cardLegalitiesId;

    @OneToOne
    private Card card;

    @Column(name = "standard")
    Legality standard;
    @Column(name = "future")
    Legality future;
    @Column(name = "historic")
    Legality historic;
    @Column(name = "pioneer")
    Legality pioneer;
    @Column(name = "modern")
    Legality modern;
    @Column(name = "legacy")
    Legality legacy;
    @Column(name = "pauper")
    Legality pauper;
    @Column(name = "vintage")
    Legality vintage;
    @Column(name = "penny")
    Legality penny;
    @Column(name = "commander")
    Legality commander;
    @Column(name = "brawl")
    Legality brawl;
    @Column(name = "duel")
    Legality duel;
    @Column(name = "oldschool")
    Legality oldschool;

    public static CardLegalities fromDto(CardLegalitiesDto dto) {
        return CardLegalities.builder()
                .standard(dto.getStandard())
                .future(dto.getFuture())
                .historic(dto.getHistoric())
                .pioneer(dto.getPioneer())
                .modern(dto.getModern())
                .legacy(dto.getLegacy())
                .pauper(dto.getPauper())
                .vintage(dto.getVintage())
                .penny(dto.getPenny())
                .commander(dto.getCommander())
                .brawl(dto.getBrawl())
                .duel(dto.getDuel())
                .oldschool(dto.getOldschool())
                .build();
    }

    public CardLegalitiesDto toDto() {
        return CardLegalitiesDto.builder()
                .standard(getStandard())
                .future(getFuture())
                .historic(getHistoric())
                .pioneer(getPioneer())
                .modern(getModern())
                .legacy(getLegacy())
                .pauper(getPauper())
                .vintage(getVintage())
                .penny(getPenny())
                .commander(getCommander())
                .brawl(getBrawl())
                .duel(getDuel())
                .oldschool(getOldschool())
                .build();
    }
}
