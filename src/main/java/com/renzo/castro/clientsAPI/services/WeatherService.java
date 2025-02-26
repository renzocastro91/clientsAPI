package com.renzo.castro.clientsAPI.services;

import com.renzo.castro.clientsAPI.clients.WeatherApiClient;
import com.renzo.castro.clientsAPI.models.Weather;
import com.renzo.castro.clientsAPI.models.dtos.WeatherResponseDTO;
import com.renzo.castro.clientsAPI.models.dtos.componentsApiResponse.CurrentWeatherDTO;
import com.renzo.castro.clientsAPI.repositories.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import java.util.Optional;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final WeatherApiClient weatherApiClient;


    @Autowired
    public WeatherService(WeatherRepository weatherRepository, WeatherApiClient weatherApiClient) {
        this.weatherRepository = weatherRepository;
        this.weatherApiClient = weatherApiClient;
    }

    public Optional<Weather> getWeatherByClientId(Long clientId) {
        return weatherRepository.findByClientId(clientId);
    }

    public Weather fetchAndSaveWeather(double latitude, double longitude, Long clientId) {
        try {
            // 🔹 Llamar a la API del clima con los parámetros recibidos
            Call<WeatherResponseDTO> call = weatherApiClient.getCurrentWeather(latitude, longitude, true);
            Response<WeatherResponseDTO> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                WeatherResponseDTO weatherResponseDTO = response.body();
                CurrentWeatherDTO weatherDTO = weatherResponseDTO.getCurrent_weather();

                // 🔹 Crear objeto `Weather`
                Weather weather = new Weather();
                weather.setTime(weatherDTO.getTime());
                weather.setTemperature(weatherDTO.getTemperature());
                weather.setWindspeed(weatherDTO.getWindspeed());
                weather.setWinddirection(weatherDTO.getWinddirection());
                weather.setIsDay(weatherDTO.getIs_day());
                weather.setWeathercode(weatherDTO.getWeathercode());
                weather.setClientId(clientId);

                // 🔹 Guardar en la base de datos
                return weatherRepository.save(weather);
            } else {
                throw new RuntimeException("Error en la API del clima: " + response.errorBody().string());
            }
        } catch (Exception e) {
            throw new RuntimeException("Fallo en la solicitud a Open-Meteo", e);
        }
    }
}




