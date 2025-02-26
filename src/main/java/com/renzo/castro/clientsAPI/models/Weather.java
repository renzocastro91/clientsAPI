package com.renzo.castro.clientsAPI.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "weather")
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private double temperature;

    @Column(nullable = false)
    private double windspeed;

    @Column(nullable = false)
    private int winddirection;

    @Column(nullable = false)
    private int isDay;

    @Column(nullable = false)
    private Integer weathercode;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    // Constructor con todos los argumentos, útil para @Builder
    public Weather(Long id, String time, double temperature, double windspeed,
                   int winddirection, int isDay, Integer weathercode, Long clientId) {
        this.id = id;
        this.time = time;
        this.temperature = temperature;
        this.windspeed = windspeed;
        this.winddirection = winddirection;
        this.isDay = isDay;
        this.weathercode = weathercode;
        this.clientId = clientId;
    }
}
