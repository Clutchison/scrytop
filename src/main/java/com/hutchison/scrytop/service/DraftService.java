package com.hutchison.scrytop.service;

import com.hutchison.scrytop.model.draft.BoosterSet;
import com.hutchison.scrytop.model.draft.Draft;
import com.hutchison.scrytop.model.draft.PrintRun;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DraftService {

    CardService cardService;

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
