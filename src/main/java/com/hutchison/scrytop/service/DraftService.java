package com.hutchison.scrytop.service;

import com.hutchison.scrytop.model.card.entity.Card;
import com.hutchison.scrytop.model.cube.CubePool;
import com.hutchison.scrytop.model.draft.BoosterSet;
import com.hutchison.scrytop.model.draft.Draft;
import com.hutchison.scrytop.model.draft.PrintRun;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DraftService {

    CardService cardService;
    static int CUBE_BOOSTER_SIZE = 15;

    @Autowired
    public DraftService(CardService cardService) {
        this.cardService = cardService;
    }

    public Draft getDraft(List<String> setIdentifiers, int playerCount) {
        if (setIdentifiers.size() != 3) {
            return null;
        }
        Map<String, PrintRun> printRuns = getPrintRuns(setIdentifiers);
        return Draft.builder()
                .playerCount(playerCount)
                .boosterSets(IntStream.range(0, playerCount)
                        .mapToObj(playerNum -> buildBoosterSet(setIdentifiers, printRuns))
                        .collect(Collectors.toList()))
                .build();
    }

    public Draft getCubeDraft(String cubeIdentifier, Integer playerCount) {
        Map<String, Card> cubeCards = cardService.getCardsByNameList(
                ServiceUtils.loadCardList("cubeLists/" + cubeIdentifier + ".txt")
        );
        validateAllCardsFound(cubeCards);
        CubePool pool = CubePool.builder()
                .cardPool(new ArrayList<>(cubeCards.values()))
                .cubeIdentifier(cubeIdentifier)
                .build();
        List<BoosterSet> boosterSets = IntStream.range(0, playerCount)
                .mapToObj(i -> BoosterSet.builder()
                        .boosters(Arrays.asList(
                                pool.getBooster(CUBE_BOOSTER_SIZE),
                                pool.getBooster(CUBE_BOOSTER_SIZE),
                                pool.getBooster(CUBE_BOOSTER_SIZE))
                        )
                        .setIdentifiers(Arrays.asList(cubeIdentifier, cubeIdentifier, cubeIdentifier))
                        .build())
                .collect(Collectors.toList());
        return Draft.builder()
                .playerCount(playerCount)
                .boosterSets(boosterSets)
                .build();
    }

    private void validateAllCardsFound(Map<String, Card> cubeCards) {
        List<String> cardsNotFound = cubeCards.entrySet().stream()
                .filter(es -> es.getValue() == null)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        if (cardsNotFound.size() > 0)
            throw new RuntimeException("Error loading cards with names: " + String.join(", ", cardsNotFound));
    }

    private BoosterSet buildBoosterSet(List<String> setIdentifiers, Map<String, PrintRun> printRuns) {
        return BoosterSet.builder()
                .setIdentifiers(setIdentifiers)
                .boosters(
                        setIdentifiers.stream()
                                .map(si -> printRuns.get(si).buildBooster())
                                .collect(Collectors.toList()))
                .build();
    }

    private Map<String, PrintRun> getPrintRuns(List<String> setIdentifiers) {
        return setIdentifiers.stream()
                .distinct()
                .collect(Collectors.toMap(
                        si -> si,
                        si -> PrintRun.build(cardService.getCardsInSet(si), si)
                ));
    }
}
