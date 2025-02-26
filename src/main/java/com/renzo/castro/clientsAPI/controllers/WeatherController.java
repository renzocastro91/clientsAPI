package com.renzo.castro.clientsAPI.controllers;

import com.renzo.castro.clientsAPI.models.Weather;
import com.renzo.castro.clientsAPI.services.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Obtener el clima de un usuario por client_id.
     *
     * @param clientId ID del cliente
     * @return Clima asociado al cliente o un 404 si no se encuentra.
     */
    @GetMapping("/{clientId}")
    public ResponseEntity<Weather> getWeatherByClientId(@PathVariable Long clientId) {
        return weatherService.getWeatherByClientId(clientId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Obtener el clima actual basado en una ubicación específica.
     *
     * @param latitude  Latitud de la ubicación.
     * @param longitude Longitud de la ubicación.
     * @param clientId  ID del cliente.
     * @return Clima actual de la ubicación proporcionada.
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
