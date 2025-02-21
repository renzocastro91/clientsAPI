package com.renzo.castro.clientsAPI.repositories;

import com.renzo.castro.clientsAPI.models.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
}