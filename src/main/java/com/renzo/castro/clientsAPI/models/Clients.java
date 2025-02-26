package com.renzo.castro.clientsAPI.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.renzo.castro.clientsAPI.validators.EmailValid;
import com.renzo.castro.clientsAPI.validators.NameValid;
import com.renzo.castro.clientsAPI.validators.PhoneValid;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name cannot be blank")
    @NameValid
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @NameValid
    @Column(nullable = false)
    private String lastName;

    @EmailValid
    @NotBlank(message = "Email cannot be blank")
    @Column(unique = true, nullable = false)
    private String email;

    @PhoneValid
    @NotBlank(message = "Phone cannot be blank")
    @Column(nullable = false)
    private String phone;

    @NotBlank(message = "Gender cannot be blank")
    @Column(nullable = false)
    private String gender;

    @NotBlank(message = "Country cannot be blank")
    @Column(nullable = false)
    private String country;

    @NotBlank(message = "City cannot be blank")
    @Column(nullable = false)
    private String city;

    @NotNull(message = "Latitude is required")
    @Column(nullable = false)
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @Column(nullable = false)
    private Double longitude;

    @NotBlank(message = "Unique key is required")
    @Column(unique = true, nullable = false)
    private String uniqueKey;

    @NotBlank(message = "UUID is required")
    @Column(unique = true, nullable = false)
    private String uuid;

    @NotBlank(message = "Username cannot be blank")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(nullable = false)
    private String password;

    // Constructor con todos los argumentos, útil para @Builder
    public Clients(Long id, String firstName, String lastName, String email, String phone,
                   String gender, String country, String city, Double latitude, Double longitude,
                   String uniqueKey, String uuid, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.uniqueKey = uniqueKey;
        this.uuid = uuid;
        this.username = username;
        this.password = password;
    }

    @JsonProperty("first")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("last")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
