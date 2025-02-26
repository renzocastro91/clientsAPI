package com.renzo.castro.clientsAPI.clients;

import com.renzo.castro.clientsAPI.models.dtos.WeatherResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import org.springframework.stereotype.Component;

@Component
public interface WeatherApiClient {

    @GET("forecast")
    Call<WeatherResponseDTO> getCurrentWeather(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("current_weather") boolean currentWeather
    );
}