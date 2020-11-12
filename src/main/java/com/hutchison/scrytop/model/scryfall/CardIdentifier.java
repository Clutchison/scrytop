package com.hutchison.scrytop.model.scryfall;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardIdentifier {
    UUID id;
    @JsonAlias("mtgo_id")
    Integer mtgoId;
    @JsonAlias("multiverse_id")
    Integer multiverseId;
    @JsonAlias("oracle_id")
    UUID oracleId;
    @JsonAlias("illustration_id")
    UUID illustrationId;
    String name;
    String set;
    @JsonAlias("collector_number")
    String collectorNumber;

    CardIdentifier(UUID id,
                   Integer mtgoId,
                   Integer multiverseId,
                   UUID oracleId,
                   UUID illustrationId,
                   String name,
                   String set,
                   String collectorNumber) {
        this.id = id;
        this.mtgoId = mtgoId;
        this.multiverseId = multiverseId;
        this.oracleId = oracleId;
        this.illustrationId = illustrationId;
        this.name = name;
        this.set = set;
        this.collectorNumber = collectorNumber;
    }

    @JsonProperty("mtgo_id")
    public Integer getMtgoId() {
        return mtgoId;
    }

    @JsonProperty("multiverse_id")
    public Integer getMultiverseId() {
        return multiverseId;
    }

    @JsonProperty("oracle_id")
    public UUID getOracleId() {
        return oracleId;
    }

    @JsonProperty("illustration_id")
    public UUID getIllustrationId() {
        return illustrationId;
    }

    @JsonProperty("collector_number")
    public String getCollectorNumber() {
        return collectorNumber;
    }


    public static class CardIdentifierBuilder {
        public CardIdentifier build() {
            validateFields();
            return new CardIdentifier(id, mtgoId, multiverseId, oracleId, illustrationId, name, set, collectorNumber);
        }

        private void validateFields() {
            Set<String> populatedFields = Arrays.stream(this.getClass().getDeclaredFields())
                    .filter(field -> {
                        try {
                            return field.get(this) != null;
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            throw new RuntimeException("Failed to reflectively get field in card identifier.");
                        }
                    })
                    .map(Field::getName)
                    .collect(Collectors.toSet());
            if (LegalFieldCombo.comboIsIllegal(populatedFields)) {
                throw new RuntimeException("Card Identifier combo is illegal: " + String.join(", ", populatedFields));
            }
        }
    }

    private enum LegalFieldCombo {
        ID("id"),
        MTGO_ID("mtgoId"),
        MULTIVERSE_ID("multiverseId"),
        ORACLE_ID("oracleId"),
        ILLUSTRATION_ID("illustrationId"),
        NAME("name"),
        NAME_SET("name", "set"),
        COLLECTOR_NUMBER_SET("collectorNumber", "set");

        private final Set<String> fieldNames;
        private static final Map<Set<String>, LegalFieldCombo> legalMap;

        static {
            legalMap = new HashMap<>();
            Stream.of(LegalFieldCombo.values())
                    .forEach(combo -> legalMap.put(combo.fieldNames, combo));
        }

        LegalFieldCombo(String... fieldNames) {
            this.fieldNames = new HashSet<>(Arrays.asList(fieldNames));
        }

        public static boolean comboIsLegal(Set<String> fieldNames) {
            return legalMap.containsKey(fieldNames);
        }

        public static boolean comboIsIllegal(Set<String> fieldNames) {
            return !legalMap.containsKey(fieldNames);
        }
    }
}
