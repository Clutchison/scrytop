package com.hutchison.scrytop.model.cube;

import com.hutchison.scrytop.model.card.entity.Card;
import com.hutchison.scrytop.model.draft.Booster;
import com.hutchison.scrytop.model.draft.BoosterCard;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Value
@Builder
public class CubePool {
    List<Card> cardPool;
    String cubeIdentifier;
    static Random random = new Random();

    public Booster getBooster(int boosterSize) {
        if (boosterSize < 1) throw new RuntimeException("Booster size must be a positive number.");
        if (boosterSize > cardPool.size()) throw new RuntimeException("Not enough cards in pool to build booster.");
        List<BoosterCard> boosterCards = new ArrayList<>();
        for (int i = 0; i < boosterSize; i++) {
            Card card = cardPool.get(random.nextInt(cardPool.size()));
            boosterCards.add(card.toBoosterCard());
            cardPool.remove(card);
        }
        return Booster.builder()
                .cards(boosterCards)
                .setIdentifier(cubeIdentifier)
                .build();
    }
}
