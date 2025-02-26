package com.renzo.castro.clientsAPI.mapper;

import com.renzo.castro.clientsAPI.models.Clients;
import com.renzo.castro.clientsAPI.models.dtos.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientsMapper {

    /**
     * Convierte un objeto Clients en UserResponseDTO.
     *
     * @param client Objeto Clients a convertir.
     * @return UserResponseDTO correspondiente.
     */
    @Mapping(source = "firstName", target = "name.first")
    @Mapping(source = "lastName", target = "name.last")
    UserResponseDTO toUserResponseDTO(Clients client);

    /**
     * Convierte un objeto UserResponseDTO en Clients.
     *
     * @param userResponseDTO Objeto UserResponseDTO a convertir.
     * @return Clients correspondiente.
     */
    @Mapping(source = "name.first", target = "firstName")
    @Mapping(source = "name.last", target = "lastName")
    @Mapping(source = "login.uuid", target = "uuid")
    @Mapping(source = "login.username", target = "username")
    @Mapping(source = "login.password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "location.city", target = "city")
    @Mapping(source = "location.country", target = "country")
    @Mapping(source = "location.coordinates.latitude", target = "latitude")
    @Mapping(source = "location.coordinates.longitude", target = "longitude")
    Clients toClients(UserResponseDTO userResponseDTO);

    /**
     * Convierte una lista de objetos Clients en una lista de UserResponseDTO.
     *
     * @param clients Lista de objetos Clients.
     * @return Lista de UserResponseDTO correspondiente.
     */
    List<UserResponseDTO> toUserResponseDTOList(List<Clients> clients);
}
