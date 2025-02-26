package com.renzo.castro.clientsAPI.mapper;

import com.renzo.castro.clientsAPI.models.Clients;
import com.renzo.castro.clientsAPI.models.dtos.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ClientsMapper {
    ClientsMapper INSTANCE = Mappers.getMapper(ClientsMapper.class);

    UserResponseDTO fromUserResponseDTO(Clients client);

    @Mapping(source = "name.first", target = "firstName")
    @Mapping(source = "name.last", target = "lastName")
    Clients fromClients(UserResponseDTO userResponseDTO);

    List<UserResponseDTO> fromListClients(List<Clients> Clients);
}
