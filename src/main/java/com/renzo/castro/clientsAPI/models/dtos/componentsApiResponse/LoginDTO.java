package com.renzo.castro.clientsAPI.models.dtos.componentsApiResponse;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginDTO {
    @Column(nullable = false, unique = true)
    private String uuid;

    private String username;
    private String password;
}

