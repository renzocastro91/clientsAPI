# 🏗️ Proyecto Spring Boot - Resolución de Actividades

Este proyecto es la resolución de las actividades propuestas en el documento **"Actividades para Spring Boot"**. Se trata de una API REST desarrollada con **Spring Boot** para la gestión de clientes y consulta de información meteorológica.

## 🚀 Tecnologías Utilizadas
- **Java 17** - Lenguaje de programación principal.
- **Spring Boot 3** - Framework para desarrollo rápido de aplicaciones Java.
- **Spring Data JPA** - Gestión de persistencia con Hibernate.
- **Spring Web** - Para la construcción de la API REST.
- **Retrofit** - Cliente HTTP para la integración con la API externa de clima.
- **Apache Camel** - Integración de rutas y procesamiento de datos.
- **H2 / SQL Server** - Base de datos para almacenamiento de clientes y clima.
- **Lombok** - Simplificación del código con anotaciones.
- **MapStruct** - Mapeo de DTOs a entidades.

## 📌 Endpoints Disponibles

### **Clientes**
- `GET /api/clients` → Obtiene todos los clientes.
- `GET /api/clients/{id}` → Obtiene un cliente por ID.
- `POST /api/clients/import` → Importa un cliente desde una API externa y guarda su información.
- `PATCH /api/clients/{id}` → Actualiza parcialmente la información de un cliente.

### **Clima**
- `GET /api/weather/{clientId}` → Obtiene el clima de un cliente por su ID.
- `GET /api/weather?latitude={lat}&longitude={lon}&clientId={clientId}` → Obtiene y guarda el clima de una ubicación específica.

## 📦 Instalación y Ejecución
### **1️⃣ Clonar el repositorio**
```sh
git clone https://github.com/tu-usuario/tu-repositorio.git
cd tu-repositorio
```

# Configurar la Base de Datos
Asegúrate de configurar la conexión a SQL Server o usa H2 en memoria para pruebas. Modifica el archivo application.properties:
# Configuración de la base de datos
spring.datasource.url=jdbc:h2:mem:clientsdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true

# Construir y ejecutar el proyecto
```sh
./gradlew build
./gradlew bootRun
```
# Acceder a la API
Una vez iniciado el servidor, accede a:
* Documentación Swagger: http://localhost:8080/swagger-ui.html
* H2 Console (si está habilitado): http://localhost:8080/h2-console

# Mejoras y Consideraciones
* Implementar validaciones más estrictas en los modelos.
* Agregar manejo de errores con @ControllerAdvice.
* Optimizar consultas a la base de datos con índices adecuados.
* Implementar caching para reducir llamadas innecesarias a la API de clima.

Desarrollado por: Castro Renzo| 📅 Año 2025
