package com.renzo.castro.clientsAPI.mapper;

import com.renzo.castro.clientsAPI.models.Clients;
import com.renzo.castro.clientsAPI.models.dtos.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface ClientsMapper {
    ClientsMapper INSTANCE = Mappers.getMapper(ClientsMapper.class);

    UserResponseDTO fromUserResponseDTO(Clients client);

    Clients fromClients(UserResponseDTO userResponseDTO);

    List<UserResponseDTO> fromListClients(List<Clients> Clients);
}
