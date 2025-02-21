package com.renzo.castro.clientsAPI.models.dtos;

import com.renzo.castro.clientsAPI.models.dtos.componentsApiResponse.*;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String gender;
    private NameDTO name;
    private LocationDTO location;
    private CoordinatesDTO coordinates;
    private String email;
    private LoginDTO login;
    private String phone;
}
