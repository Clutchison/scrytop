package com.hutchison.scrytop.model.scryfall.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hutchison.scrytop.model.scryfall.CardIdentifier;
import com.hutchison.scrytop.model.scryfall.ScryfallCardDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("list")
public class CollectionResponse extends ScryfallResponse {
    @JsonAlias("not_found")
    List<CardIdentifier> notFound;
    @JsonAlias("data")
    List<ScryfallCardDto> cards;
}
