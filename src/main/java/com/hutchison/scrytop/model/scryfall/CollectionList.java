package com.hutchison.scrytop.model.scryfall;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CollectionList {
    List<CardIdentifier> identifiers;
}
