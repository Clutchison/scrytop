package com.hutchison.scrytop.model.draft;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class BoosterSet {

    List<String> setIdentifiers;
    List<Booster> boosters;

    private BoosterSet(List<String> setIdentifiers, List<Booster> boosters) {
        this.setIdentifiers = setIdentifiers;
        this.boosters = boosters;
    }

    public static class BoosterSetBuilder {
        public BoosterSet build() {
            if (setIdentifiers == null || setIdentifiers.size() != 3)
                throw new RuntimeException("Error building BoosterSet: Invalid setIdentifiers");
            if (boosters == null || boosters.size() != 3)
                throw new RuntimeException("Error building BoosterSet: Invalid boosters");
            return new BoosterSet(setIdentifiers, boosters);
        }
    }
}
