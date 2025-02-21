package com.renzo.castro.clientsAPI.controllers;
import com.renzo.castro.clientsAPI.models.Weather;
import com.renzo.castro.clientsAPI.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    /**
     * Obtener el clima para un usuario específico
     */
    @GetMapping("/{clientId}")
    public Optional<Weather> getWeatherByClientId(@PathVariable Long clientId) {
        return weatherService.getWeatherByClientId(clientId);
    }
}
