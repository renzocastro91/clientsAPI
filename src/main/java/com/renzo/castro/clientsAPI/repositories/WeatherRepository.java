package com.renzo.castro.clientsAPI.repositories;

import com.renzo.castro.clientsAPI.models.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    @Transactional(readOnly = true)
    Optional<Weather> findByClientId(Long clientId);
}
