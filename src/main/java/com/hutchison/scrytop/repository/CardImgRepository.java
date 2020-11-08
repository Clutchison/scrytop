package com.hutchison.scrytop.repository;

import com.hutchison.scrytop.model.card.entity.Card;
import com.hutchison.scrytop.model.card.entity.CardImage;
import com.hutchison.scrytop.model.card.enums.CardImgType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardImgRepository extends JpaRepository<CardImage, Long> {

    Optional<CardImage> findByCardAndType(Card card, CardImgType type);
}
