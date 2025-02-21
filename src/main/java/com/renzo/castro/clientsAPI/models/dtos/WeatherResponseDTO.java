package com.renzo.castro.clientsAPI.models.dtos;

import lombok.Data;

@Data
public class WeatherResponseDTO {
    private CurrentWeatherDTO current_weather;

    @Data
    public static class CurrentWeatherDTO {
        private String time;
        private double temperature;
        private double windspeed;
        private int winddirection;
        private boolean is_day;
        private int weathercode;
    }
}