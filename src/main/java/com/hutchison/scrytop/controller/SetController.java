package com.hutchison.scrytop.controller;

import com.hutchison.scrytop.model.card.entity.Card;
import com.hutchison.scrytop.service.CardService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/set")
public class SetController {

    CardService cardService;

    @Autowired
    public SetController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/{setAbrev}")
    public ResponseEntity<List<Card>> getCardsInSet(@PathVariable String setAbrev) {
        List<Card> cards = cardService.getCardsInSet(setAbrev);
        return cards.size() == 0 ? ResponseEntity.badRequest().build() : ResponseEntity.ok(cards);
    }

    @GetMapping("/{setAbrev}/color/{colorChar}")
    public ResponseEntity<List<Card>> getCardsInSetByColor(@PathVariable String setAbrev,
                                                           @PathVariable char colorChar) {
        List<Card> cards = cardService.getCardsInSetByColor(setAbrev, colorChar);
        return cards.size() == 0 ? ResponseEntity.badRequest().build() : ResponseEntity.ok(cards);
    }
}
