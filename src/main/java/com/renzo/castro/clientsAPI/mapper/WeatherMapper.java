package com.renzo.castro.clientsAPI.mapper;

import com.renzo.castro.clientsAPI.models.Weather;
import com.renzo.castro.clientsAPI.models.dtos.WeatherResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface WeatherMapper {
    WeatherMapper INSTANCE = Mappers.getMapper(WeatherMapper.class);

    WeatherResponseDTO fromWeatherResponseDTO(Weather weather);

    Weather fromWeather(WeatherResponseDTO weatherResponseDTO);

    List<WeatherResponseDTO> fromListWeather(List<Weather> weather);
}
