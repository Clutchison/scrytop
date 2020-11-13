package com.hutchison.scrytop.model.draft;

import com.hutchison.scrytop.model.card.entity.CardFace;
import com.hutchison.scrytop.model.card.entity.CardImageUris;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class BoosterCard {
    String name;
    CardImageUris imageURIs;
}
