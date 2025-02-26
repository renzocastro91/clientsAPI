package com.renzo.castro.clientsAPI.services;

import com.renzo.castro.clientsAPI.clients.WeatherApiClient;
import com.renzo.castro.clientsAPI.models.Weather;
import com.renzo.castro.clientsAPI.models.dtos.WeatherResponseDTO;
import com.renzo.castro.clientsAPI.models.dtos.componentsApiResponse.CurrentWeatherDTO;
import com.renzo.castro.clientsAPI.repositories.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final WeatherApiClient weatherApiClient;

    /**
     * Obtener el clima de un cliente por clientId.
     */
    public Optional<Weather> getWeatherByClientId(Long clientId) {
        return weatherRepository.findByClientId(clientId);
    }

    /**
     * Obtiene el clima desde la API externa y lo guarda en la base de datos.
     *
     * @param latitude  Latitud del cliente.
     * @param longitude Longitud del cliente.
     * @param clientId  ID del cliente.
     * @return Entidad Weather con la información guardada.
     */
    public Weather fetchAndSaveWeather(double latitude, double longitude, Long clientId) {
        validarParametros(latitude, longitude, clientId);

        try {
            // 🔹 Llamada a la API externa
            Call<WeatherResponseDTO> call = weatherApiClient.getCurrentWeather(latitude, longitude, true);
            Response<WeatherResponseDTO> response = call.execute();

            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Error en la API del clima: " + response.errorBody().string());
            }

            // 🔹 Extraer datos de la respuesta
            WeatherResponseDTO weatherResponseDTO = response.body();
            CurrentWeatherDTO weatherDTO = weatherResponseDTO.getCurrent_weather();

            // 🔹 Mapear la respuesta a la entidad Weather
            Weather weather = Weather.builder()
                    .time(weatherDTO.getTime())
                    .temperature(weatherDTO.getTemperature())
                    .windspeed(weatherDTO.getWindspeed())
                    .winddirection(weatherDTO.getWinddirection())
                    .isDay(weatherDTO.getIs_day())
                    .weathercode(weatherDTO.getWeathercode())
                    .clientId(clientId)
                    .build();

            // 🔹 Guardar en la base de datos
            Weather savedWeather = weatherRepository.save(weather);
            log.info("Clima guardado correctamente para el cliente con ID: {}", clientId);
            return savedWeather;

        } catch (IOException e) {
            log.error("Error en la solicitud a Open-Meteo: {}", e.getMessage());
            throw new RuntimeException("Fallo en la solicitud a Open-Meteo", e);
        }
    }

    /**
     * Valida los parámetros antes de hacer la solicitud.
     */
    private void validarParametros(double latitude, double longitude, Long clientId) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitud inválida: debe estar entre -90 y 90.");
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitud inválida: debe estar entre -180 y 180.");
        }
        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("El clientId debe ser un valor positivo.");
        }
    }
}



