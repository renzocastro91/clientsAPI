package com.renzo.castro.clientsAPI.repositories;

import com.renzo.castro.clientsAPI.models.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long> {

    @Transactional(readOnly = true)
    Optional<Clients> findByUuid(String uuid);

    @Transactional(readOnly = true)
    Optional<Clients> findByUsername(String username);
}
