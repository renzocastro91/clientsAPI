package com.renzo.castro.clientsAPI.client;

import com.renzo.castro.clientsAPI.models.dtos.WeatherResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiClient {

    @GET("v1/forecast")
    Call<WeatherResponseDTO> getWeather(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("current_weather") boolean currentWeather
    );
}
