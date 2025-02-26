package com.renzo.castro.clientsAPI.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserRoute extends RouteBuilder {

    private static final String API_URL = "https://randomuser.me/api/";

    @Override
    public void configure() {

        // 🔹 Manejo de excepciones
        onException(Exception.class)
                .log("❌ Error al llamar a la API de usuarios: ${exception.message}")
                .handled(true);

        // 🔹 Ruta para obtener múltiples usuarios
        from("direct:getUsers")
                .routeId("getUsersRoute")
                .setHeader("results", simple("${header.results} != null ? ${header.results} : 1"))
                .log("📤 Solicitando ${header.results} usuarios desde la API...")
                .toD(API_URL + "?results=${header.results}")
                .log("✅ Respuesta recibida: ${body}");

        // 🔹 Ruta para obtener un solo usuario
        from("direct:fetchUser")
                .routeId("fetchUserRoute")
                .log("📤 Solicitando un usuario aleatorio...")
                .toD(API_URL + "?results=1")
                .log("✅ Respuesta recibida: ${body}");
    }
}

