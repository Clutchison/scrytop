package com.hutchison.scrytop.controller;

import com.hutchison.scrytop.model.card.dto.CardDto;
import com.hutchison.scrytop.model.card.entity.Card;
import com.hutchison.scrytop.model.card.enums.CardImgType;
import com.hutchison.scrytop.service.CardImgService;
import com.hutchison.scrytop.service.CardService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/card")
public class CardController {

    CardService cardService;
    CardImgService cardImgService;

    @Autowired
    public CardController(CardService cardService, CardImgService cardImgService) {
        this.cardService = cardService;
        this.cardImgService = cardImgService;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CardDto> getCardByName(@PathVariable String name) {
        Optional<Card> cardByName = cardService.getCardByName(name);
        return cardByName.map(card -> ResponseEntity.ok(card.toDto()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}/fuzzy")
    public List<Card> getCardsByName(@PathVariable String name) {
        return cardService.getCardsByName(name);
    }

    @GetMapping("/token/name/{name}")
    public ResponseEntity<Card> getTokenByName(@PathVariable String name) {
        Optional<Card> cardByName = cardService.getTokenByName(name);
        return cardByName.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/img/{name}/{type}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getCardImgByName(@PathVariable String name, @PathVariable String type) {
        Optional<byte[]> img = cardImgService.getImageByCardName(name, CardImgType.valueOf(type.toUpperCase()));
        return img.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
