package com.hutchison.scrytop.model.scryfall.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "object")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ErrorResponse.class, name = "error"),
        @JsonSubTypes.Type(value = CollectionResponse.class, name = "list")
})
public class ScryfallResponse {
    String object;
}
