package com.renzo.castro.clientsAPI.mapper;

import com.renzo.castro.clientsAPI.models.Weather;
import com.renzo.castro.clientsAPI.models.dtos.WeatherResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

    @Mapping(source = "time", target = "current_weather.time")
    @Mapping(source = "temperature", target = "current_weather.temperature")
    @Mapping(source = "windspeed", target = "current_weather.windspeed")
    @Mapping(source = "winddirection", target = "current_weather.winddirection")
    @Mapping(source = "isDay", target = "current_weather.is_day")
    @Mapping(source = "weathercode", target = "current_weather.weathercode")
    WeatherResponseDTO toWeatherResponseDTO(Weather weather);

    @Mapping(source = "current_weather.time", target = "time")
    @Mapping(source = "current_weather.temperature", target = "temperature")
    @Mapping(source = "current_weather.windspeed", target = "windspeed")
    @Mapping(source = "current_weather.winddirection", target = "winddirection")
    @Mapping(source = "current_weather.is_day", target = "isDay")
    @Mapping(source = "current_weather.weathercode", target = "weathercode")
    Weather toWeather(WeatherResponseDTO weatherResponseDTO);

    List<WeatherResponseDTO> toWeatherResponseDTOList(List<Weather> weather);
}
