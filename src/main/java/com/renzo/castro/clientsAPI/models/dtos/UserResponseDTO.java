package com.renzo.castro.clientsAPI.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.renzo.castro.clientsAPI.models.dtos.componentsApiResponse.*;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDTO {
    private String gender;
    private NameDTO name;
    private LocationDTO location;
    private String email;
    private LoginDTO login;
    private String phone;
    private String cell;
}

