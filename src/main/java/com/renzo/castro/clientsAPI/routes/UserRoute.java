package com.renzo.castro.clientsAPI.routes;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:fetchUser")
                .toD("https://randomuser.me/api/?results=1")
                .convertBodyTo(String.class);
    }
}
