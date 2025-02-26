package com.renzo.castro.clientsAPI.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.renzo.castro.clientsAPI.models.Clients;
import com.renzo.castro.clientsAPI.models.Weather;
import com.renzo.castro.clientsAPI.models.dtos.UserResponseDTO;
import com.renzo.castro.clientsAPI.repositories.ClientsRepository;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.List;


@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final ProducerTemplate producerTemplate;
    private final ClientsRepository clientsRepository;
    private final ObjectMapper objectMapper;
    private final WeatherService weatherService;

    @Autowired
    public UserService(ProducerTemplate producerTemplate,
                       ClientsRepository clientsRepository,
                       ObjectMapper objectMapper,
                       WeatherService weatherService) {
        this.producerTemplate = producerTemplate;
        this.clientsRepository = clientsRepository;
        this.objectMapper = objectMapper;
        this.weatherService = weatherService;
    }

    /**
     * Obtener todos los clientes
     */
    public List<Clients> getAllClients() {
        return clientsRepository.findAll();
    }

    /**
     * Obtener cliente por ID
     */
    public Optional<Clients> getClientById(Long id) {
        return clientsRepository.findById(id);
    }

    /**
     * Actualizar cliente por ID de forma parcial
     */
    public Optional<Clients> updateClient(Long id, Clients updatedClient) {
        return clientsRepository.findById(id).map(existingClient -> {
            updateClientFields(existingClient, updatedClient);
            return clientsRepository.save(existingClient);
        });
    }

    private void updateClientFields(Clients existingClient, Clients updatedClient) {
        if (updatedClient.getFirstName() != null) existingClient.setFirstName(updatedClient.getFirstName());
        if (updatedClient.getLastName() != null) existingClient.setLastName(updatedClient.getLastName());
        if (updatedClient.getEmail() != null) existingClient.setEmail(updatedClient.getEmail());
        if (updatedClient.getPhone() != null) existingClient.setPhone(updatedClient.getPhone());
        if (updatedClient.getGender() != null) existingClient.setGender(updatedClient.getGender());
        if (updatedClient.getCountry() != null) existingClient.setCountry(updatedClient.getCountry());
        if (updatedClient.getCity() != null) existingClient.setCity(updatedClient.getCity());
        if (updatedClient.getUsername() != null) existingClient.setUsername(updatedClient.getUsername());
        if (updatedClient.getPassword() != null) existingClient.setPassword(updatedClient.getPassword());
    }

    /**
     * Importar un usuario desde la API externa, mapearlo y guardar su clima correctamente
     */
    @Transactional
    public Clients fetchAndSaveUser() {
        logger.info("Llamando a la API de clientes...");

        String response = producerTemplate.requestBody("direct:fetchUser", null, String.class);

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode result = root.path("results").get(0);

            // Validación del UUID
            String uuid = result.path("login").path("uuid").asText();
            if (uuid == null || uuid.isEmpty()) {
                throw new IllegalArgumentException("El UUID del usuario es nulo o vacío.");
            }

            // Si el usuario ya existe, lo retornamos
            Optional<Clients> existingUser = clientsRepository.findByUuid(uuid);
            if (existingUser.isPresent()) {
                logger.info("El usuario con UUID {} ya existe en la base de datos.", uuid);
                return existingUser.get();
            }

            // Mapeo del JSON al DTO
            UserResponseDTO userDTO = objectMapper.treeToValue(result, UserResponseDTO.class);

            // Creación del cliente
            Clients user = mapUserDTOToEntity(userDTO, uuid);
            clientsRepository.save(user);
            logger.info("Usuario guardado correctamente con UUID: {}", uuid);

            // Guardado del clima asociado al usuario
            Weather weather = weatherService.fetchAndSaveWeather(user.getLatitude(), user.getLongitude(), user.getId());
            logger.info("Clima guardado correctamente para el usuario con ID: {}", user.getId());

            return user;
        } catch (Exception e) {
            logger.error("Error al procesar la API de usuario", e);
            throw new RuntimeException("Error al procesar la API de usuario", e);
        }
    }

    private Clients mapUserDTOToEntity(UserResponseDTO userDTO, String uuid) {
        Clients user = new Clients();
        user.setUuid(uuid);
        user.setFirstName(userDTO.getName().getFirst());
        user.setLastName(userDTO.getName().getLast());
        user.setGender(userDTO.getGender());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getLogin().getUsername());
        user.setPassword(userDTO.getLogin().getPassword());
        user.setPhone(userDTO.getPhone());

        // Asignación de ubicación
        if (userDTO.getLocation() != null) {
            user.setCity(userDTO.getLocation().getCity());
            user.setCountry(userDTO.getLocation().getCountry());

            if (userDTO.getLocation().getCoordinates() != null) {
                user.setLatitude(userDTO.getLocation().getCoordinates().getLatitude());
                user.setLongitude(userDTO.getLocation().getCoordinates().getLongitude());
            }
        }

        // Generación de clave única
        user.setUniqueKey("USER-" + uuid);
        return user;
    }
}
