package com.renzo.castro.clientsAPI.services;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.renzo.castro.clientsAPI.models.dtos.UserResponseDTO;
import com.renzo.castro.clientsAPI.models.Clients;
import com.renzo.castro.clientsAPI.repositories.ClientsRepository;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class UserService {

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

    /**
     * Actualizar parcialmente un cliente
     */
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

    public Clients fetchAndSaveUser() {
        String response = producerTemplate.requestBody("direct:fetchUser", null, String.class);

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode result = root.path("results").get(0);

            String uuid = result.path("login").path("uuid").asText();

            // Verificar si el usuario ya existe
            Optional<Clients> existingUser = clientsRepository.findByUuid(uuid);
            if (existingUser.isPresent()) {
                return existingUser.get();
            }

            // Crear y guardar usuario
            Clients user = Clients.builder()
                    .gender(result.path("gender").asText())
                    .firstName(result.path("name").path("first").asText())
                    .lastName(result.path("name").path("last").asText())
                    .city(result.path("location").path("city").asText())
                    .country(result.path("location").path("country").asText())
                    .latitude(result.path("location").path("coordinates").path("latitude").asText())
                    .longitude(result.path("location").path("coordinates").path("longitude").asText())
                    .email(result.path("email").asText())
                    .uuid(uuid)
                    .username(result.path("login").path("username").asText())
                    .password(result.path("login").path("password").asText())
                    .phone(result.path("phone").asText())
                    .uniqueKey(uuid)
                    .build();

            clientsRepository.save(user);

            // Llamar a la API del clima después de guardar al usuario
            weatherService.fetchAndSaveWeather(user);

            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al procesar la API de usuario");
        }
    }
}