package com.renzo.castro.clientsAPI.config;

import com.renzo.castro.clientsAPI.clients.WeatherApiClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Configuración de Retrofit para la integración con la API de Open-Meteo.
 * Usa `OkHttpClient` con interceptor de logs para depuración.
 */
@Configuration
public class RetrofitConfig {

    private static final String BASE_URL = "https://api.open-meteo.com/v1/";
    private static final int TIMEOUT = 10; // Tiempo de espera en segundos

    /**
     * Configura un cliente HTTP con interceptor de logs para facilitar la depuración.
     * @return Instancia de OkHttpClient configurada.
     */
    @Bean
    public OkHttpClient okHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Configura Retrofit con OkHttpClient y un conversor de JSON basado en Jackson.
     * @param okHttpClient Cliente HTTP inyectado.
     * @return Instancia de Retrofit configurada.
     */
    @Bean
    public Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    /**
     * Crea e inyecta el cliente de API para obtener el clima.
     * @param retrofit Instancia de Retrofit inyectada.
     * @return Implementación de WeatherApiClient generada por Retrofit.
     */
    @Bean
    public WeatherApiClient weatherApiClient(Retrofit retrofit) {
        return retrofit.create(WeatherApiClient.class);
    }
}

