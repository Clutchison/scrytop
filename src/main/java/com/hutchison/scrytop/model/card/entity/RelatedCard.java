package com.hutchison.scrytop.model.card.entity;

import com.hutchison.scrytop.model.card.dto.RelatedCardDto;
import com.hutchison.scrytop.model.card.enums.CardComponent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.net.URI;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

@Entity
@Table(name = "RELATED_CARD")
@Builder
@EqualsAndHashCode
public class RelatedCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "related_card_id")
    Long cardRelatedCardId;

    @Column(name = "uuid", nullable = false, unique = true)
    UUID uuid;
    @Column(name = "component", nullable = false)
    CardComponent component;
    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "type_line", nullable = false)
    String typeLine;
    @Column(name = "uri", nullable = false)
    URI uri;

    public static RelatedCard fromDto(RelatedCardDto dto) {
        return RelatedCard.builder()
                .uuid(dto.getUuid())
                .component(dto.getComponent())
                .name(dto.getName())
                .typeLine(dto.getTypeLine())
                .uri(dto.getUri())
                .build();
    }

    public RelatedCardDto toDto() {
        return RelatedCardDto.builder()
                .uuid(getUuid())
                .component(getComponent())
                .name(getName())
                .typeLine(getTypeLine())
                .uri(getUri())
                .build();
    }
}
