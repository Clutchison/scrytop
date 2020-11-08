package com.hutchison.scrytop.model.card.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hutchison.scrytop.model.card.enums.Legality;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardLegalitiesDto {
    Legality standard;
    Legality future;
    Legality historic;
    Legality pioneer;
    Legality modern;
    Legality legacy;
    Legality pauper;
    Legality vintage;
    Legality penny;
    Legality commander;
    Legality brawl;
    Legality duel;
    Legality oldschool;
}
