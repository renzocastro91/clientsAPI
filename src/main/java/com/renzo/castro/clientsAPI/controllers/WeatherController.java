package com.renzo.castro.clientsAPI.controllers;

import com.renzo.castro.clientsAPI.models.Weather;
import com.renzo.castro.clientsAPI.models.dtos.WeatherResponseDTO;
import com.renzo.castro.clientsAPI.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    /**
     * Obtener el clima de un usuario por client_id
     */
    @GetMapping("/{clientId}")
    public ResponseEntity<Weather> getWeatherByClientId(@PathVariable Long clientId) {
        Optional<Weather> weather = weatherService.getWeatherByClientId(clientId);
        return weather.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Obtener el clima actual basado en una ubicación específica.
     *
     * @param latitude  Latitud de la ubicación.
     * @param longitude Longitud de la ubicación.
     * @param clientId id del cliente
     * @return Clima actual.
     */
    @GetMapping
    public ResponseEntity<Weather> getWeather(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("clientId") Long clientId) {

        Weather weatherData = weatherService.fetchAndSaveWeather(latitude, longitude, clientId);
        return ResponseEntity.ok(weatherData);
    }
}
