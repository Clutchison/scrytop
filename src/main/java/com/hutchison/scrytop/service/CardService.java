package com.hutchison.scrytop.service;

import com.hutchison.scrytop.model.card.entity.Card;
import com.hutchison.scrytop.model.card.enums.CardImgType;
import com.hutchison.scrytop.model.card.enums.Color;
import com.hutchison.scrytop.model.scryfall.ScryfallCardDto;
import com.hutchison.scrytop.repository.CardRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        Optional<Card> postgresCard = cardRepository.findByName(name).stream()
                .filter(card -> !card.getSetType().equals("token")) // Excludes token from search.
                .findFirst();
        if (postgresCard.isPresent()) return postgresCard;
        Optional<ScryfallCardDto> scryfallCard = scryfallService.getCardByName(name);
        scryfallCard.ifPresent(scryfallCardDto -> cardRepository.save(Card.fromScryfallDto(scryfallCardDto)));
        return scryfallCard.map(Card::fromScryfallDto);
    }

    public List<Card> getCardsByName(String name) {
        throw new NotYetImplementedException();
//        List<Card> cards = cardRepository.findByNameLike(likeifyString(name)).stream()
//                .filter(card -> !card.getSetType().equals("token"))
//                .collect(Collectors.toList());
//        Optional<Card> exactMatch = cards.stream()
//                .filter(c -> c.getName().toLowerCase().equals(name.toLowerCase()))
//                .findFirst();
//        return exactMatch.map(Arrays::asList).orElse(cards);
    }

    public Map<String, Card> getCardsByNameList(List<String> names) {
        if (names == null) return new HashMap<>();
        Map<String, Card> postgresCards = cardRepository.findByNameIn(names).stream()
                .collect(Collectors.toMap(
                        Card::getName,
                        card -> card
                ));
        // Fix for cards without exact names. (cards with split names usually)
        names.stream()
                .filter(name -> postgresCards.get(name) == null)
                .forEach(name -> {
                    Optional<Card> foundCard = cardRepository.findFirstByNameLike(likeifyString(name));
                    foundCard.ifPresent(card -> postgresCards.put(name, card));
                });
        Map<String, Card> scryfallCards = scryfallService.getCardsByNameList(names.stream()
                .filter(name -> postgresCards.get(name) == null)
                .collect(Collectors.toList()));
        cardRepository.saveAll(scryfallCards.values());
        return names.stream()
                .collect(Collectors.toMap(
                        name -> name,
                        name -> postgresCards.get(name) != null ? postgresCards.get(name) : scryfallCards.get(name)
                ));
    }

    public Optional<Card> getTokenByName(String name) {
        return cardRepository.findByName(name).stream()
                .filter(c -> c.getSetType().equals("token"))
                .findFirst();
    }

    public List<Card> getCardsInSet(String setAbrev) {
        return cardRepository.findBySet(setAbrev);
    }

    public Optional<byte[]> getImageByCardName(String name, CardImgType type) {
        Optional<Card> card = getCardByName(name);
//        return card.isPresent() ?
//                getCardImageUriByType(card.get(), type) : Optional.empty();
        throw new NotYetImplementedException();
    }

    public List<Card> getCardsInSetByColor(String setAbrev, char colorChar) {
        Color color = Color.fromCharacter(colorChar);
        return cardRepository.findBySet(setAbrev).stream()
                .filter(c -> c.getColors().isColor(color))
                .collect(Collectors.toList());
    }

    public Optional<String> fixImageURIs() {
        List<Card> cardsToFix = cardRepository.findByNullCardImageUri();
        if (cardsToFix.size() == 0) return Optional.of("No cards to fix.");
        List<String> cardNames = cardsToFix.stream().map(Card::getName).collect(Collectors.toList());
        cardRepository.deleteAll(cardsToFix);
        Map<String, Card> scryfallCards = scryfallService.getCardsByNameList(cardNames);
        cardRepository.saveAll(scryfallCards.values().stream().filter(Objects::nonNull).collect(Collectors.toList()));
        return Optional.of(
                "Results: \n" +
                        scryfallCards.entrySet().stream()
                                .map(es -> es.getKey() + ": " + (es.getValue() == null ? "Fail" : "Success"))
                                .collect(Collectors.joining("\n"))
        );
    }

    private URI getCardImageUriByType(Card card, CardImgType type) {
        switch (type) {
            case PNG:
                return card.getImageURIs().getPng();
            case SMALL:
                return card.getImageURIs().getSmall();
            case NORMAL:
                return card.getImageURIs().getNormal();
            case LARGE:
                return card.getImageURIs().getLarge();
            case ARTCROP:
                return card.getImageURIs().getArtCrop();
            case BORDERCROP:
                return card.getImageURIs().getBorderCrop();
            default:
                return null;
        }
    }
}
