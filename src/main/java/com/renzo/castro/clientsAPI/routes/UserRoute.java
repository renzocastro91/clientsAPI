package com.renzo.castro.clientsAPI.routes;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserRoute extends RouteBuilder {

    private static final String API_URL = "https://randomuser.me/api/?results=";

    @Override
    public void configure() {
        from("direct:getUsers")
                .routeId("getUsersRoute")
                .log("Llamando a la API de usuarios con ${header.results} resultados")
                .toD(API_URL + "${header.results}")
                .log("Respuesta de la API de usuarios: ${body}");

        from("direct:fetchUser") // 🔹 Define la ruta esperada
                .routeId("fetchUserRoute")
                .log("Llamando a la API de usuarios")
                .toD(API_URL) // 🔹 Llama a la API de randomuser.me
                .log("Respuesta de la API de usuarios: ${body}");
    }


}
