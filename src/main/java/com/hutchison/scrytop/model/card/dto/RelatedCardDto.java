package com.hutchison.scrytop.model.card.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hutchison.scrytop.model.card.enums.CardComponent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.net.URI;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RelatedCardDto {
    @NonNull
    @JsonAlias("id")
    UUID uuid;
    @NonNull
    CardComponent component;
    @NonNull
    String name;
    @NonNull
    @JsonAlias("type_line")
    String typeLine;
    @NonNull
    URI uri;
}
