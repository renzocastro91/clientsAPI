package com.renzo.castro.clientsAPI;

import TestDatabaseConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestDatabaseConfig.class) // Carga la configuración de pruebas
class ClientsApiApplicationTests {

	@Test
	void contextLoads() {
	}
}
