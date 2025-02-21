package com.renzo.castro.clientsAPI.models.dtos.componentsApiResponse;

import lombok.Data;

@Data
public class LoginDTO {
    private String uuid;
    private String username;
    private String password;
}
