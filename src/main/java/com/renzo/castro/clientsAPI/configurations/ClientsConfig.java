package com.renzo.castro.clientsAPI.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Configuración de la base de datos y serialización JSON.
 */
@Configuration
public class ClientsConfig {

    /**
     * Configuración del DataSource para la base de datos de clientes.
     * Se inyectan las propiedades definidas en `application.yml` o `application.properties`
     * bajo el prefijo `clients-api-db.datasource`.
     *
     * @return Instancia de {@link DataSource} configurada.
     */
    @Bean(name = "clientsAPI")
    @ConfigurationProperties(prefix = "clients-api-db.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Configura un ObjectMapper para manejar JSON con convención `snake_case`.
     * Esto es útil si la API con la que interactuamos usa nombres de propiedades en snake_case.
     *
     * @return Instancia de {@link ObjectMapper} configurada.
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }
}

