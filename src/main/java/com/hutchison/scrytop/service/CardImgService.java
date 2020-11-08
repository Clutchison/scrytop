package com.hutchison.scrytop.service;

import com.hutchison.scrytop.model.card.entity.Card;
import com.hutchison.scrytop.model.card.entity.CardImage;
import com.hutchison.scrytop.model.card.enums.CardImgType;
import com.hutchison.scrytop.repository.CardImgRepository;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@Service
@Value
public class CardImgService {

    CardService cardService;
    RestTemplate restTemplate;
    CardImgRepository cardImgRepository;

    @Autowired
    CardImgService(CardService cardService, RestTemplate restTemplate, CardImgRepository cardImgRepository) {
        this.cardService = cardService;
        this.restTemplate = restTemplate;
        this.cardImgRepository = cardImgRepository;
    }

    public Optional<byte[]> getImageByCardName(String name, CardImgType type) {
        Optional<Card> cardOpt = cardService.getCardByName(name);
        if (cardOpt.isPresent()) {
            Card card = cardOpt.get();
            Optional<CardImage> imgOpt = cardImgRepository.findByCardAndType(card, type);
            if (imgOpt.isPresent()) {
                return Optional.ofNullable(imgOpt.get().getContent());
            } else {
                URI imageUri = getCardImageUriByType(card, type);
                byte[] content = restTemplate.getForObject(imageUri, byte[].class);
                cardImgRepository.save(CardImage.builder().type(type).content(content).card(card).build());
                return Optional.ofNullable(content);
            }
        } else {
            return Optional.empty();
        }
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
