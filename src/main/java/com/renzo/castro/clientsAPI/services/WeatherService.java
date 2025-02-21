package com.renzo.castro.clientsAPI.services;

import com.renzo.castro.clientsAPI.client.WeatherApiClient;
import com.renzo.castro.clientsAPI.models.dtos.WeatherResponseDTO;
import com.renzo.castro.clientsAPI.models.Clients;
import com.renzo.castro.clientsAPI.models.Weather;
import com.renzo.castro.clientsAPI.repositories.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Optional;

@Service
public class WeatherService {

    @Autowired
    private WeatherApiClient weatherApiClient;

    @Autowired
    private WeatherRepository weatherRepository;

    /**
     * Obtener el clima de un usuario por ID
     */
    public Optional<Weather> getWeatherByClientId(Long clientId) {
        return weatherRepository.findById(clientId);
    }

    public void fetchAndSaveWeather(Clients client) {
        try {
            Call<WeatherResponseDTO> call = weatherApiClient.getWeather(
                    client.getLatitude(), client.getLongitude(), true
            );

            Response<WeatherResponseDTO> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                WeatherResponseDTO weatherDTO = response.body();

                Weather weather = Weather.builder()
                        .time(weatherDTO.getCurrent_weather().getTime())
                        .temperature(weatherDTO.getCurrent_weather().getTemperature())
                        .windspeed(weatherDTO.getCurrent_weather().getWindspeed())
                        .winddirection(weatherDTO.getCurrent_weather().getWinddirection())
                        .isDay(weatherDTO.getCurrent_weather().is_day())
                        .weathercode(weatherDTO.getCurrent_weather().getWeathercode())
                        .client(client)
                        .build();

                weatherRepository.save(weather);
            } else {
                throw new RuntimeException("Error al obtener el clima");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al procesar la API del clima");
        }
    }
}
