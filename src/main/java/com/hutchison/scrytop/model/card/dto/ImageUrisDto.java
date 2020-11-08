package com.hutchison.scrytop.model.card.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageUrisDto {
    URI small;
    URI normal;
    URI large;
    URI png;
    URI artCrop;
    URI borderCrop;
}
