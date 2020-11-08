package com.hutchison.scrytop.model.draft;

import com.hutchison.scrytop.model.card.entity.Card;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;


@Builder
@ToString
@EqualsAndHashCode
@FieldDefaults(level = PRIVATE)
public class IndexedCardList {
    Integer index;
    final List<Card> cardList;

    private IndexedCardList(Integer index, List<Card> cardList) {
        this.index = index;
        this.cardList = cardList;
    }

    public Card next() {
        Card card = cardList.get(index);
        index = (index + 1) % cardList.size();
        return card;
    }

    public static class IndexedCardListBuilder {
        public IndexedCardList build() {
            if (cardList == null) throw new RuntimeException("Error building IndexedCardList: null card list");
            if (index == null) index = 0;
            return new IndexedCardList(index, cardList);
        }
    }
}
