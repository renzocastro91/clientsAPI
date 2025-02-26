package com.renzo.castro.clientsAPI.repositories;

import com.renzo.castro.clientsAPI.models.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Optional<Weather> findByClientId(@Param("clientId") Long clientId);
}
