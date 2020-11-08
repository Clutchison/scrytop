package com.hutchison.scrytop.model.draft;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Draft {

    Integer playerCount;
    List<BoosterSet> boosterSets;

    private Draft(int playerCount, List<BoosterSet> boosterSets) {
        this.playerCount = playerCount;
        this.boosterSets = boosterSets;
    }

    public static class DraftBuilder {
        public Draft build() {
            if (playerCount == null)
                throw new RuntimeException("Error building Draft: Invalid playerCount");
            if (boosterSets == null || boosterSets.size() != playerCount)
                throw new RuntimeException("Error building Draft: Invalid boosterSets");
            return new Draft(playerCount, boosterSets);
        }
    }
}
