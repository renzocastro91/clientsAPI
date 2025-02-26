package com.renzo.castro.clientsAPI.models.dtos.componentsApiResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NameDTO {
    private String title;
    private String first;
    private String last;
}
