package com.renzo.castro.clientsAPI.repositories;

import com.renzo.castro.clientsAPI.models.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientsRepository extends JpaRepository<Clients, Long> {
    Optional<Clients> findByUuid(String uuid);
}