package com.renzo.castro.clientsAPI.controllers;

import com.renzo.castro.clientsAPI.models.Clients;
import com.renzo.castro.clientsAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/{id}")
    public ResponseEntity<Clients> getClientById(@PathVariable Long id) {
        return userService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // ✅ Evitamos devolver Optional
    }

    /**
     * Importar un nuevo cliente desde la API externa y obtener su clima
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
