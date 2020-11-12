package com.hutchison.scrytop.repository;

import com.hutchison.scrytop.model.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByNameLike(String name);

    Optional<Card> findFirstByNameLike(String name);

    List<Card> findByName(String name);

    List<Card> findBySet(String setAbrev);

    List<Card> findByNameIn(List<String> names);
}
