package com.hutchison.scrytop.repository;

import com.hutchison.scrytop.model.card.entity.RelatedCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatedCardRepository extends JpaRepository<RelatedCard, Long> {

}
