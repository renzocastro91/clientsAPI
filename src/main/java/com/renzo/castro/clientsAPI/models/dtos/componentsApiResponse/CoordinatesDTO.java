package com.renzo.castro.clientsAPI.models.dtos.componentsApiResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoordinatesDTO {
    private double latitude;
    private double longitude;
}

