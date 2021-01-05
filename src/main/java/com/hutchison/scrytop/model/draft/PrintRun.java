package com.hutchison.scrytop.model.draft;

import com.hutchison.scrytop.model.card.entity.Card;
import com.hutchison.scrytop.model.card.enums.ColorCombo;
import com.hutchison.scrytop.model.card.enums.Rarity;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hutchison.scrytop.model.card.enums.Rarity.*;

@Value
public class PrintRun {

    String setIdentifier;
    IndexedCardList mythics;
    IndexedCardList rares;
    IndexedCardList uncommons;
    Map<ColorCombo, IndexedCardList> commons;
    IndexedCardList commonsRemoval;

    private PrintRun(String setIdentifier,
                     IndexedCardList mythics,
                     IndexedCardList rares,
                     IndexedCardList uncommons,
                     Map<ColorCombo, IndexedCardList> commons,
                     IndexedCardList commonsRemoval) {
        this.setIdentifier = setIdentifier;
        this.mythics = mythics;
        this.rares = rares;
        this.uncommons = uncommons;
        this.commons = commons;
        this.commonsRemoval = commonsRemoval;
    }

    public Booster buildBooster() {
        List<Card> cards = new ArrayList<>();
        cards.add(ThreadLocalRandom.current().nextInt(8) == 0 ? mythics.next() : rares.next());
        IntStream.range(0, 3).forEach(i -> cards.add(uncommons.next()));

        commons.forEach((colors, cl) -> cards.add(cl.next()));

        // todo: Booster logic
        return Booster.builder()
                .setIdentifier(setIdentifier)
                .cards(cards.stream().map(Card::toBoosterCard).collect(Collectors.toList()))
                .build();
    }

    public static PrintRun build(List<Card> masterList, String setIdentifier) {
        if (masterList == null || masterList.size() == 0)
            throw new RuntimeException("Error building PrintRun: null or empty masterList");
        if (setIdentifier == null)
            throw new RuntimeException("Error building PrintRun: invalid setIdentifier");
        Map<Rarity, List<Card>> rarityMap = masterList.stream()
                .collect(Collectors.groupingBy(Card::getRarity));
        List<Card> allCommons = masterList.stream()
                .filter(c -> c.getRarity().equals(COMMON))
                .collect(Collectors.toList());
        List<Card> removalCommons = allCommons.stream()
                .filter(Card::isCommonRemoval)
                .collect(Collectors.toList());
        Collections.shuffle(removalCommons);
        Map<ColorCombo, List<Card>> nonRemovalCommonsByColor = allCommons.stream()
                .filter(c -> !c.isCommonRemoval())
                .collect(Collectors.groupingBy(Card::getColors));
        nonRemovalCommonsByColor.values().forEach(Collections::shuffle);
        Map<ColorCombo, Float> nonRemovalCommonPercentages =
                computeNonRemovalCommonPercentages(nonRemovalCommonsByColor, removalCommons);
        return new PrintRun(
                setIdentifier,
                IndexedCardList.builder().cardList(staggerColors(rarityMap.get(MYTHIC))).build(),
                IndexedCardList.builder().cardList(staggerColors(rarityMap.get(RARE))).build(),
                IndexedCardList.builder().cardList(staggerColors(rarityMap.get(UNCOMMON))).build(), nonRemovalCommonsByColor.entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        es -> IndexedCardList.builder().cardList(es.getValue()).build()
                )),
                IndexedCardList.builder().cardList(removalCommons).build()
        );
    }

    private static Map<ColorCombo, Float> computeNonRemovalCommonPercentages(
            Map<ColorCombo, List<Card>> nonRemovalCommonsByColor, List<Card> removalCommons) {
        throw new UnsupportedOperationException();
    }

    private static List<Card> staggerColors(List<Card> cards) {
        Collections.shuffle(cards);
        Map<ColorCombo, List<Card>> cardsSeparatedByColor = cards.stream()
                .collect(Collectors.groupingBy(Card::getColors));
        List<ColorCombo> colorOrder = new ArrayList<>(cardsSeparatedByColor.keySet());
        Collections.shuffle(colorOrder);
        int maxListSize = cardsSeparatedByColor.values().stream().mapToInt(List::size).max().orElse(0);
        int startingCardIndex = ThreadLocalRandom.current().nextInt(0, maxListSize);

        List<Card> staggeredList = new ArrayList<>();
        IntStream.range(0, maxListSize).forEach(indexIncrement ->
                colorOrder.forEach(colors -> {
                    List<Card> cardList = cardsSeparatedByColor.get(colors);
                    Card card = cardList.get((startingCardIndex + indexIncrement) % cardList.size());
                    staggeredList.add(card);
                }));
        return staggeredList;
    }
}
