package com.renzo.castro.clientsAPI.config;
import com.renzo.castro.clientsAPI.client.WeatherApiClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RetrofitConfig {

    @Bean
    public WeatherApiClient weatherApiClient() {
        return new Retrofit.Builder()
                .baseUrl("https://api.open-meteo.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(WeatherApiClient.class);
    }
}
