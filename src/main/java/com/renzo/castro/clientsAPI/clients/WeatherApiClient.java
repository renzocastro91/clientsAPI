package com.renzo.castro.clientsAPI.clients;

import com.renzo.castro.clientsAPI.models.dtos.WeatherResponseDTO;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Cliente de la API de clima utilizando Retrofit.
 */
@Component
public interface WeatherApiClient {

    /**
     * Consulta el clima actual en base a la latitud y longitud.
     * @param latitude Coordenada de latitud.
     * @param longitude Coordenada de longitud.
     * @param currentWeather Indica si se debe obtener el clima actual.
     * @return WeatherResponseDTO encapsulado en un `Call<>` de Retrofit.
     */
    @GET("forecast")
    Call<WeatherResponseDTO> getCurrentWeather(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("current_weather") boolean currentWeather
    );

    /**
     * Metodo de utilidad para manejar llamadas de Retrofit con respuestas seguras.
     * @param call Llamada Retrofit a ejecutar.
     * @return Respuesta procesada o `null` si hubo un error.
     */
    default WeatherResponseDTO executeCall(Call<WeatherResponseDTO> call) {
        try {
            return call.execute().body();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el clima de la API", e);
        }
    }
}
