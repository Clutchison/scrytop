package com.hutchison.scrytop.service;

import com.hutchison.scrytop.model.card.entity.Card;
import com.hutchison.scrytop.model.card.enums.Color;
import com.hutchison.scrytop.repository.CardRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hutchison.scrytop.service.ServiceUtils.likeifyString;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardService {

    CardRepository cardRepository;
    ScryfallService scryfallService;

    @Autowired
    public CardService(CardRepository cardRepository,
                       ScryfallService scryfallService) {
        this.cardRepository = cardRepository;
        this.scryfallService = scryfallService;
    }

    public Optional<Card> getCardByName(String name) {

        return scryfallService.getCardByName(name);
//        return cardRepository.findByName(name).stream()
//                .filter(card -> !card.getSetType().equals("token"))
//                .findFirst();
    }

    public List<Card> getCardsByName(String name) {
        List<Card> cards = cardRepository.findByNameLike(likeifyString(name)).stream()
                .filter(card -> !card.getSetType().equals("token"))
                .collect(Collectors.toList());
        Optional<Card> exactMatch = cards.stream()
                .filter(c -> c.getName().toLowerCase().equals(name.toLowerCase()))
                .findFirst();
        return exactMatch.map(Arrays::asList).orElse(cards);
    }

    public Optional<Card> getTokenByName(String name) {
        return cardRepository.findByName(name).stream()
                .filter(c -> c.getSetType().equals("token"))
                .findFirst();
    }

    public List<Card> getCardsInSet(String setAbrev) {
        return cardRepository.findBySet(setAbrev);
    }

    public List<Card> getCardsInSetByColor(String setAbrev, char colorChar) {
        Color color = Color.fromCharacter(colorChar);
        return cardRepository.findBySet(setAbrev).stream()
                .filter(c -> c.getColors().isColor(color))
                .collect(Collectors.toList());
    }
}
