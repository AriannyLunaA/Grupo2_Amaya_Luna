# Grupo2_Amaya_Luna
Desarrollo de proyecto semestral. 

Microservicio: 
ms-cliente
Encargado de la persistencia y gestión de los datos maestros de los clientes del servicio técnico.
--Funcionalidades Implementadas 
  
Estructuración CSR: Implementación completa del patrón Controller-Service-Repository.  

Persistencia Real: Gestión de base de datos MySQL (db_clientes) mediante JPA e Hibernate.  
Validaciones Robustas: Uso de Bean Validation (JSR 380) para asegurar la integridad de campos como RUT, Email y Teléfono.  
Manejo de Excepciones: Respuestas controladas mediante ResponseEntity y códigos de estado HTTP adecuados.  

 --Tecnologías Utilizadas
Java 21
Spring Boot 4.0.6   
Spring Data JPA
Lombok 

--Endpoints REST  
Funcionalidad	Método	URL
Crear Cliente	POST	/api/clientes
Listar Todos	GET	/api/clientes
Buscar por RUT	GET	/api/clientes/buscar-rut?rut={rut}
Eliminar Cliente	DELETE	/api/clientes/{id}

--Pasos para la Ejecución 
 
 Requisitos previos:

Tener instalado MySQL Server.
Contar con el JDK 21 configurado.

--Base de Datos:
Crear la base de datos manualmente con CREATE DATABASE db_clientes; o permitir que el proyecto la cree mediante la propiedad createDatabaseIfNotExist=true en application.properties. 
 
--Configuración:
Ajustar las credenciales de MySQL en src/main/resources/application.properties.

--Ejecución:
Ejecutar la clase MsClienteApplication.java desde IntelliJ IDEA.  El servicio estará disponible en el puerto 8082.



----------------------------------
--*Documentación de la API con Swagger*--

El microservicio de **Inventario** cuenta con documentación interactiva mediante Swagger/OpenAPI UI.

* **URL de acceso local:** [http://localhost:8084/swagger-ui/index.html](http://localhost:8084/swagger-ui/index.html)
* **Puerto del servicio:** `8084`

### Endpoints Disponibles:
* `GET /api/v1/inventario` - Obtener la lista completa de repuestos en stock.
* `GET /api/v1/inventario/{id}` - Buscar un repuesto específico por su ID único.
* `POST /api/v1/inventario/` - Registrar un nuevo repuesto en el sistema.
* `PUT /api/v1/inventario/{id}/descontar` - Disminuir el stock disponible de un artículo.
--------------------------------------

---*Documentación de la API de Tickets con Swagger*---

El microservicio de **Tickets** tiene su propia documentación interactiva para pruebas de endpoints.

* **URL de acceso local:** [http://localhost:8085/swagger-ui/index.html](http://localhost:8085/swagger-ui/index.html)
* **Puerto del servicio:** `8085`

### Endpoints Disponibles:
* `GET /api/v1/ticket` - Obtener la lista completa de tickets registrados.
* `GET /api/v1/ticket/{id}` - Buscar un ticket específico por su ID único .
* `POST /api/v1/ticket` - Crear y registrar un nuevo ticket de soporte en el sistema.

--------------------------------------------------------

---*Documentación de la API de Diagnósticos con Swagger*---

El microservicio de **Diagnósticos** cuenta con una interfaz interactiva de Swagger para la validación de sus operaciones.

* **URL de acceso local:** [http://localhost:8088/swagger-ui/index.html](http://localhost:8088/swagger-ui/index.html)
* **Puerto del servicio:** `8088`

### Endpoints Disponibles:
* `GET /api/v1/diagnostico` - Obtener la lista de diagnósticos técnicos emitidos.
* `GET /api/v1/diagnostico/{id}` - Buscar un diagnóstico específico por su ID único.
* `POST /api/v1/diagnostico` - Registrar un nuevo reporte de diagnóstico en el sistema.
-------------------------------------------------------------------

---*Documentación de JUnit*----

**Actualizaciones de Infraestructura y QA (ms-inventario)**

1. Aislamiento de Entornos: Implementación de perfiles de configuración de Spring (dev y test),
separando las variables de entorno en application-dev.properties y application-test.properties.
2. Base de Datos Dedicada para Pruebas: Configuración de un esquema físico independiente (fixnow_inventario_test)
gestionado y validado automáticamente por Flyway, evitando la contaminación de los datos de desarrollo.

**Refactorización de Pruebas Unitarias (Capa Data Access):

1. Reubicación estructural de las clases de prueba (RepuestoRepositoryTest) 
para garantizar la carga correcta del contexto de Spring Boot.
2. Estandarización del código de pruebas aplicando convenciones de legibilidad (@DisplayName),
trazabilidad (@Slf4j), y aislamiento de estado mediante ejecución transaccional con rollback automático 
(@Transactional).

**Actualizaciones de Infraestructura y QA (ms-diagnostico y ms-ticket)
Estabilización de Entornos:**

Corrección de la infraestructura de pruebas para ms-diagnostico y ms-ticket,
asegurando el uso de perfiles test independientes.

Creación de esquemas de base de datos dedicados (fixnow_diagnostico_test, fixnow_tickets_test).

Implementación de pruebas unitarias en DiagnosticoRepositoryTest cubriendo: persistencia exitosa,
validaciones @NotNull (campo estado) y restricciones de longitud (@Size).
Ajuste de modelos y DTOs para cumplir con las reglas de negocio técnicas del sistema.

