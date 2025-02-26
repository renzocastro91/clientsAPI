package com.renzo.castro.clientsAPI.models.dtos.componentsApiResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherUnitsDTO {
    private String time;
    private String interval;
    private String temperature;
    private String windspeed;
    private String winddirection;
    private String is_day;
    private String weathercode;
}
