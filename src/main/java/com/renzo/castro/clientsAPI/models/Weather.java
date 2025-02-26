package com.renzo.castro.clientsAPI.models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "weather")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String time;
    private double temperature;
    private double windspeed;
    private int winddirection;
    private int isDay;
    private int weathercode;

    @Column(name = "client_id", nullable = false)
    private Long clientId;
}
