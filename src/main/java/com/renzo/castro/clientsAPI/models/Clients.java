package com.renzo.castro.clientsAPI.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, message = "First name must have at least 2 characters")
    @JsonProperty("first")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @JsonProperty("last")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Phone cannot be blank")
    private String phone;

    @NotBlank(message = "Gender cannot be blank")
    private String gender;

    @NotBlank(message = "Country cannot be blank")
    private String country;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "Latitude is required")
    private Double latitude;

    @NotBlank(message = "Longitude is required")
    private Double longitude;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Unique key is required")
    private String uniqueKey;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "UUID is required")
    private String uuid;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;

}
