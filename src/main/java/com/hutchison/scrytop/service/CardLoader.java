package com.hutchison.scrytop.service;

import com.hutchison.scrytop.model.card.dto.RelatedCardDto;
import com.hutchison.scrytop.model.scryfall.ScryfallCardDto;
import com.hutchison.scrytop.model.card.entity.Card;
import com.hutchison.scrytop.model.card.entity.RelatedCard;
import com.hutchison.scrytop.repository.CardRepository;
import com.hutchison.scrytop.repository.RelatedCardRepository;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Value
@Slf4j
public class CardLoader {

    CardRepository cardRepository;
    RelatedCardRepository relatedCardRepository;
//    String scryfallJson;

    @Autowired
    CardLoader(CardRepository cardRepository,
               RelatedCardRepository relatedCardRepository) {
//               @Qualifier("scryfallJson") String scryfallJson) {
        this.cardRepository = cardRepository;
        this.relatedCardRepository = relatedCardRepository;
//        this.scryfallJson = scryfallJson;
    }

    public void load() {
        Set<ScryfallCardDto> dtos = new HashSet<>();
//        Set<ScryfallCardDto> dtos = readJson();
        log.debug("DTO size: " + dtos.size());
        Map<ScryfallCardDto, Set<RelatedCard>> savedRelatedCards = saveRelatedCards(dtos);
        Set<Card> cards = savedRelatedCards.entrySet().stream()
                .map(es -> Card.fromScryfallDto(es.getKey(), es.getValue()))
                .collect(Collectors.toSet());
//        cardRepository.saveAll(cards);
    }

    private Map<ScryfallCardDto, Set<RelatedCard>> saveRelatedCards(Set<ScryfallCardDto> cards) {
        Set<RelatedCard> collect = cards.stream()
                .filter(card -> card.getAllParts() != null)
                .flatMap(card -> card.getAllParts().stream()
                        .map(RelatedCard::fromDto))
                .collect(Collectors.toSet());
        Set<RelatedCard> savedRelatedCards = new HashSet<>(relatedCardRepository.saveAll(
                collect));

        return cards.stream().collect(Collectors.toMap(
                card -> card,
                card -> {
                    Set<UUID> rcUuids = card.getAllParts() == null ? new HashSet<>() :
                            card.getAllParts().stream()
                                    .map(RelatedCardDto::getUuid)
                                    .collect(Collectors.toSet());
                    return savedRelatedCards.stream()
                            .filter(sc -> rcUuids.contains(sc.getUuid()))
                            .collect(Collectors.toSet());
                }));
    }

    private void printMaxSizes(Set objs, Class clazz) {
        Map<Field, Integer> stringFields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.getType().isAssignableFrom(String.class))
                .collect(Collectors.toMap(
                        f -> f,
                        f -> 0
                ));

        objs.forEach(card -> {
            stringFields.keySet().forEach(field -> {
                try {
                    String strValue = (String) field.get(card);
                    Integer length = strValue == null ? 0 : strValue.length();
                    if (stringFields.get(field) < length) stringFields.put(field, length);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getCause());
                }
            });
        });
        System.out.println("Fields greater than or equal to 255:");
        stringFields.entrySet().stream()
                .filter(es -> es.getValue() >= 255)
                .forEach(es -> System.out.println(es.getKey().getName() + ": " + es.getValue()));
        System.out.println("Fields less than 255:");
        stringFields.entrySet().stream()
                .filter(es -> es.getValue() < 255)
                .forEach(es -> System.out.println(es.getKey().getName() + ": " + es.getValue()));
    }

//    public Set<ScryfallCardDto> readJson() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            return new HashSet<>(
//                    Arrays.asList(objectMapper.readValue(scryfallJson, ScryfallCardDto[].class)));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Failed to read json, " + e.getCause());
//        }
//    }
}
