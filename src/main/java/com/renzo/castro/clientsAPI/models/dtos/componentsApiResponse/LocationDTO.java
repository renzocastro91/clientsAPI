package com.renzo.castro.clientsAPI.models.dtos.componentsApiResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDTO {
    private StreetDTO street;
    private String city;
    private String state;
    private String country;
    private String postcode;
    private CoordinatesDTO coordinates;
    private TimezoneDTO timezone;
}
