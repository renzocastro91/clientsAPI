package com.renzo.castro.clientsAPI.controllers;

import com.renzo.castro.clientsAPI.models.Clients;
import com.renzo.castro.clientsAPI.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de clientes.
 */
@RestController
@RequestMapping("/api/clients")
public class ClientsAPIController {

    private final UserService userService;

    /**
     * Inyección de dependencias a través del constructor (mejor práctica que @Autowired en campo).
     */
    public ClientsAPIController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Obtiene la lista de todos los clientes.
     *
     * @return Lista de clientes almacenados.
     */
    @GetMapping
    public ResponseEntity<List<Clients>> getAllClients() {
        List<Clients> clients = userService.getAllClients();
        return clients.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(clients);
    }

    /**
     * Obtiene un cliente por su ID.
     *
     * @param id Identificador del cliente.
     * @return Cliente encontrado o respuesta 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Clients> getClientById(@PathVariable Long id) {
        return userService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Importa un nuevo cliente desde la API externa y obtiene su clima.
     *
     * @return Cliente importado.
     */
    @PostMapping("/import")
    public ResponseEntity<Clients> importUser() {
        Clients client = userService.fetchAndSaveUser();
        return ResponseEntity.ok(client);
    }

    /**
     * Actualiza parcialmente un cliente.
     *
     * @param id     ID del cliente a actualizar.
     * @param client Datos parciales del cliente.
     * @return Cliente actualizado o respuesta 404 si no existe.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Clients> updateClient(@PathVariable Long id, @RequestBody Clients client) {
        return userService.updateClient(id, client)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

