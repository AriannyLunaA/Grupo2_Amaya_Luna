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




