package com.hutchison.scrytop.model.draft;

import com.hutchison.scrytop.model.card.entity.Card;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class Booster {

    String setIdentifier;
    List<BoosterCard> cards;

    private Booster(String setIdentifier, List<BoosterCard> cards) {
        this.setIdentifier = setIdentifier;
        this.cards = cards;
    }

    public static class BoosterBuilder {
        public Booster build() {
            if (this.setIdentifier == null)
                throw new RuntimeException("Error building booster: setIdentifier null");
            if (this.cards == null)
                throw new RuntimeException("Error building booster: cards null");
            return new Booster(setIdentifier, cards);
        }
    }
}
