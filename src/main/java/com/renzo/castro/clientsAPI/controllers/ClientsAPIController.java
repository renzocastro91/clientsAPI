package com.renzo.castro.clientsAPI.controllers;

import com.renzo.castro.clientsAPI.models.Clients;
import com.renzo.castro.clientsAPI.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientsAPIController {

    @Autowired
    private UserService userService;

    /**
     * Obtener todos los clientes
     */
    @GetMapping
    public List<Clients> getAllClients() {
        return userService.getAllClients();
    }

    /**
     * Obtener un cliente por ID
     */
    @GetMapping("/{id}")
    public Optional<Clients> getClientById(@PathVariable Long id) {
        return userService.getClientById(id);
    }

    /**
     * Importar un nuevo cliente desde la API externa
     */
    @PostMapping("/import")
    public Clients importUser() {
        return userService.fetchAndSaveUser();
    }

    /**
     * Actualizar parcialmente un cliente
     */
    @PatchMapping("/{id}")
    public Clients updateClient(@PathVariable Long id, @RequestBody Clients client) {
        return userService.updateClient(id, client);
    }
}
