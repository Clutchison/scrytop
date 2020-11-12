package com.hutchison.scrytop.model.scryfall.response;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("error")
public class ErrorResponse extends ScryfallResponse {
    Integer status;
    String code;
    String details;
    String type;
    List<String> warnings;
}
