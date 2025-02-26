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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WeatherService weatherService;

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

    public Clients updateClient(Long id, Clients updatedClient) {
        return clientsRepository.findById(id).map(existingClient -> {
            if (updatedClient.getFirstName() != null) existingClient.setFirstName(updatedClient.getFirstName());
            if (updatedClient.getLastName() != null) existingClient.setLastName(updatedClient.getLastName());
            if (updatedClient.getEmail() != null) existingClient.setEmail(updatedClient.getEmail());
            if (updatedClient.getPhone() != null) existingClient.setPhone(updatedClient.getPhone());
            if (updatedClient.getGender() != null) existingClient.setGender(updatedClient.getGender());
            if (updatedClient.getCountry() != null) existingClient.setCountry(updatedClient.getCountry());
            if (updatedClient.getCity() != null) existingClient.setCity(updatedClient.getCity());
            if (updatedClient.getUsername() != null) existingClient.setUsername(updatedClient.getUsername());
            if (updatedClient.getPassword() != null) existingClient.setPassword(updatedClient.getPassword());

            return clientsRepository.save(existingClient);
        }).orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));
    }

    /**
     * Importar un usuario desde la API externa, mapearlo y guardar su clima correctamente
     */
    public Clients fetchAndSaveUser() {
        logger.info("Llamando a la API de clientes...");

        String response = producerTemplate.requestBody("direct:fetchUser", null, String.class);

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode result = root.path("results").get(0);

            // Verificar si existe UUID
            String uuid = result.path("login").path("uuid").asText();
            if (uuid == null || uuid.isEmpty()) {
                throw new RuntimeException("El UUID del usuario es nulo o vacío.");
            }

            // Verificar si el usuario ya existe en la base de datos
            Optional<Clients> existingUser = clientsRepository.findByUuid(uuid);
            if (existingUser.isPresent()) {
                logger.info("El usuario con UUID {} ya existe en la base de datos.", uuid);
                return existingUser.get();
            }

            // 🔹 Convertimos JSON en UserResponseDTO
            UserResponseDTO userDTO = objectMapper.treeToValue(result, UserResponseDTO.class);

            // 🔹 Crear la entidad Clients y asignar los valores correctamente
            Clients user = new Clients();
            user.setUuid(uuid);
            user.setFirstName(userDTO.getName().getFirst());
            user.setLastName(userDTO.getName().getLast());
            user.setGender(userDTO.getGender());
            user.setEmail(userDTO.getEmail());
            user.setUsername(userDTO.getLogin().getUsername());
            user.setPassword(userDTO.getLogin().getPassword());
            user.setPhone(userDTO.getPhone());

            // Manejar datos de localización
            if (userDTO.getLocation() != null) {
                user.setCity(userDTO.getLocation().getCity());
                user.setCountry(userDTO.getLocation().getCountry());

                if (userDTO.getLocation().getCoordinates() != null) {
                    double latitude = userDTO.getLocation().getCoordinates().getLatitude();
                    double longitude = userDTO.getLocation().getCoordinates().getLongitude();

                    user.setLatitude(latitude);
                    user.setLongitude(longitude);
                }
            }

            // Generar un `uniqueKey` para evitar el `PropertyValueException`
            user.setUniqueKey("USER-" + uuid);

            // 🔹 Guardar el usuario en la base de datos
            clientsRepository.save(user);
            logger.info("Usuario guardado correctamente con UUID: {}", uuid);

            // 🔹 Guardar el clima usando el WeatherService
            Weather weather = weatherService.fetchAndSaveWeather(
                    user.getLatitude(),
                    user.getLongitude(),
                    user.getId()
            );
            logger.info("Clima guardado correctamente para el usuario con ID: {}", user.getId());

            return user;
        } catch (Exception e) {
            logger.error("Error al procesar la API de usuario", e);
            throw new RuntimeException("Error al procesar la API de usuario", e);
        }
    }
}