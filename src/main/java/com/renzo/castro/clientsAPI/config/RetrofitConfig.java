package com.renzo.castro.clientsAPI.config;

import com.renzo.castro.clientsAPI.clients.WeatherApiClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfig {

    private static final String BASE_URL = "https://api.open-meteo.com/v1/";

    @Bean
    public Retrofit retrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Bean
    public WeatherApiClient weatherApiClient(Retrofit retrofit) {
        return retrofit.create(WeatherApiClient.class);
    }
}

