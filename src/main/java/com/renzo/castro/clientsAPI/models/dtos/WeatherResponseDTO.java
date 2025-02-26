package com.renzo.castro.clientsAPI.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.renzo.castro.clientsAPI.models.dtos.componentsApiResponse.CurrentWeatherDTO;
import com.renzo.castro.clientsAPI.models.dtos.componentsApiResponse.CurrentWeatherUnitsDTO;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponseDTO {
    private double latitude;
    private double longitude;
    private double generationtime_ms;
    private int utc_offset_secondsM;
    private String timezone;
    private String timezone_abbreviation;
    private double elevation;
    private CurrentWeatherUnitsDTO current_weather_units;
    private CurrentWeatherDTO current_weather;
}
