# 🛠️ FixNow - Sistema Distribuido de Soporte Técnico

![](https://img.shields.io/badge/Java-21-E34F26?style=flat&logo=openjdk&logoColor=white) ![](https://img.shields.io/badge/Spring%20Boot-4.0.x-6DB33F?style=flat&logo=springboot&logoColor=white) ![](https://img.shields.io/badge/Eureka-Discovery-00B4EB?style=flat&logo=spring&logoColor=white) ![](https://img.shields.io/badge/MySQL-Database-4479A1?style=flat&logo=mysql&logoColor=white) ![](https://img.shields.io/badge/Flyway-Migrations-CC0200?style=flat&logo=flyway&logoColor=white) ![](https://img.shields.io/badge/IDE-IntelliJ%202026.x-000000?style=flat&logo=intellijidea&logoColor=white) ![](https://img.shields.io/badge/GitHub-Version%20Control-181717?style=flat&logo=github&logoColor=white) ![](https://img.shields.io/badge/Grupo-2-8A2BE2?style=flat)

> **FixNow** es un sistema backend robusto construido bajo el paradigma de **Arquitectura de Microservicios**. Su objetivo principal es orquestar, registrar y automatizar el flujo operativo completo de un servicio técnico: desde el ingreso del equipo por parte del cliente, pasando por el diagnóstico técnico y el control de stock de repuestos, hasta la facturación, notificación y auditoría inmutable de eventos.

---

## 📑 Tabla de Contenidos

* 👥 [Equipo de Desarrollo (Grupo 2)](#-equipo-de-desarrollo-grupo-2)
* 🏗️ [Topología de la Arquitectura Distribuida](#-topología-de-la-arquitectura-distribuida)
* ⚙️ [Stack Tecnológico y Patrones de Diseño Implementados](#-stack-tecnológico-y-patrones-de-diseño-implementados)
* 🛤️ [Diagrama de Flujo y Orquestación del Negocio](#-diagrama-de-flujo-y-orquestación-del-negocio)
* 🚀 [Guía de Despliegue Local (Entorno de Desarrollo)](#-guía-de-despliegue-local-entorno-de-desarrollo)

---

## 👥 Equipo de Desarrollo (Grupo 2)
Este sistema ha sido diseñado, desarrollado y orquestado mediante control de versiones en GitHub por:
- **Jesús Amaya** - [@Jesusamayap](https://github.com/Jesusamayap)
- **Arianny Luna** - [@AriannyLunaA](https://github.com/AriannyLunaA)
- **Benjamín Aliste** - [@shirokamidev](https://github.com/shirokamidev)

---

## 🏗️ Topología de la Arquitectura Distribuida

Para asegurar los principios de **alta cohesión y bajo acoplamiento**, el monolito tradicional ha sido fragmentado en **10 microservicios** funcionales. Cada uno es responsable de un dominio de negocio altamente específico y posee su propia base de datos, garantizando la independencia tecnológica y resiliencia ante fallos.

| Servicio | Puerto Local | Base de Datos (MySQL) | Dominio / Responsabilidad Principal |
| :--- | :---: | :--- | :--- |
| `ms-registry` | **8761** | *N/A (Memoria)* | Servidor Eureka para descubrimiento y registro dinámico de servicios. |
| `ms-auth` | **8082** | `fixnow_auth` | Control de credenciales de usuario y emisión de accesos. |
| `ms-persona` | **8083** | `fixnow_persona` | Gestión integral de clientes y personal técnico (CRUD base). |
| `ms-equipo` | **8081** | `fixnow_equipo` | Inventario de hardware ingresado, asociado estrictamente a una persona. |
| `ms-inventario` | **8084** | `fixnow_inventario` | Catálogo de repuestos, precios y control estricto de stock físico. |
| `ms-ticket` | **8085** | `fixnow_ticket` | Emisión de órdenes de trabajo (entrelaza lógicamente a Persona y Equipo). |
| `ms-diagnostico`| **8088** | `fixnow_diagnostico` | Revisiones técnicas, estimación de viabilidad, tiempo, costo y repuestos. |
| `ms-pago` | **8087** | `fixnow_pago` | Motor transaccional para la liquidación de servicios realizados. |
| `ms-auditoria` | **8090** | `fixnow_auditoria` | Trazabilidad cruzada del sistema (Logs inmutables de todas las acciones). |
| `ms-notificacion`| **8091** | `fixnow_notificacion` | Generación y despacho de alertas informativas por correo al cliente. |

---

## ⚙️ Stack Tecnológico y Patrones de Diseño Implementados

El desarrollo del backend fue realizado utilizando **IntelliJ IDEA 2026.x**, haciendo uso intensivo del ecosistema de **Spring Cloud** y las mejores prácticas de ingeniería de software. A continuación, el detalle técnico de las herramientas aplicadas:

### 1. Entorno de Base de Datos (Laragon & MySQL)
Para el almacenamiento persistente, utilizamos el motor relacional **MySQL**. Para estandarizar el entorno de desarrollo local del equipo, utilizamos **Laragon**, un entorno universal portátil y de alto rendimiento que levanta el motor de MySQL en el puerto `3306`.

### 2. Persistencia y ORM (JPA & Hibernate)
En lugar de escribir sentencias SQL manuales y repetitivas, implementamos **Jakarta Persistence API (JPA)** junto con **Hibernate** como proveedor ORM.
Mediante la abstracción de `JpaRepository`, mapeamos directamente nuestras clases Java (`@Entity`) a las tablas relacionales. Esto agiliza las operaciones CRUD básicas y nos permite generar consultas automáticas como `findByIdTicket(Long id)`.

### 3. Versionamiento de Esquemas (Flyway)
Dado que estamos en una arquitectura distribuida donde cada microservicio es "dueño" de su base de datos, implementamos **Flyway** para mantener un control de versiones estricto de la estructura de datos:
- **Migraciones controladas:** Cada microservicio posee un directorio `db/migration` con scripts nativos (ej. `V1__crea_tabla_usuarios.sql`).
- **Seguridad Estructural:** Flyway crea las tablas (`DDL`) e inserta los registros de prueba (`DML`) en el arranque.
- **Validación de Integridad:** Se configuró JPA en modo `validate` (`spring.jpa.hibernate.ddl-auto=validate`) para asegurar que las entidades en código Java coincidan exactamente con la estructura creada en MySQL, evitando corrupciones estructurales.

### 4. Service Discovery (Netflix Eureka)
Para evitar el "hardcoding" de direcciones IP y facilitar el escalamiento, implementamos un servidor de registro centralizado (`ms-registry`).
Todos los microservicios están configurados como clientes (`@EnableDiscoveryClient`) y se anuncian en el servidor al arrancar. La resolución de rutas se realiza lógicamente por el nombre del servicio.

### 5. Comunicación Síncrona Declarativa (OpenFeign)
En un ecosistema fragmentado, los servicios necesitan "hablar" entre sí. En lugar de utilizar complejas llamadas manuales HTTP (como `RestTemplate`), implementamos **Spring Cloud OpenFeign**.

**¿Cómo funciona en el sistema?** Feign nos permite abstraer una petición HTTP transformándola en una simple interfaz Java. Por ejemplo, cuando el servicio `ms-ticket` necesita verificar que un equipo realmente existe antes de generar una orden, simplemente llama al método de la interfaz `EquipoClient` como si fuera un método local. Por debajo, Feign, apoyado en Eureka, busca la IP dinámica del servicio `ms-equipo` y ejecuta la validación, manteniendo nuestro código limpio y altamente legible.

### 6. Aislamiento y Transferencia (Patrón DTO)
Para no exponer el modelo de base de datos (`@Entity`) en la red, implementamos rigurosamente el patrón **Data Transfer Object (DTO)**. Los DTOs permiten:
- Empaquetar y transferir **solo la información estrictamente necesaria** entre servicios.
- Evitar problemas de recursividad (ciclos infinitos) al convertir relaciones bidireccionales de base de datos a formato JSON.
- Mantener los microservicios independientes estructuralmente.

### 7. Semántica REST y Respuestas HTTP (`ResponseEntity`)
La API respeta de forma estricta los estándares RESTful. Para controlar a bajo nivel la cabecera, el cuerpo y el código de estado HTTP de nuestras respuestas, envolvemos todos los retornos del Controlador con la clase `ResponseEntity`.
Manejamos semánticamente los códigos:
- `200 OK`: Consultas exitosas.
- `201 CREATED`: Persistencia exitosa de nuevas entidades (Ej. al emitir un Ticket).
- `204 NO CONTENT`: Consultas válidas pero que devuelven listas vacías.
- `400 BAD REQUEST`: Violaciones a reglas de negocio o DTOs mal formados.
- `404 NOT FOUND`: Recursos inexistentes en la base de datos según el ID buscado.

### 8. Integridad de Datos (Bean Validation JSR 380)
Para proteger nuestras bases de datos de información corrupta, implementamos validaciones desde la capa del Controlador mediante la especificación **Jakarta Bean Validation**.
Se inyectan anotaciones directamente en las propiedades de las clases (`@NotNull`, `@NotBlank`, `@Size`, `@Min`, `@Email`). El controlador intercepta las peticiones inyectando la etiqueta `@Valid` en el `@RequestBody`, rebotando la petición automáticamente con un error `400 Bad Request` antes de que afecte la capa de servicio.

### 9. Observabilidad y Trazabilidad (SLF4J + Lombok)
Para monitorizar el comportamiento del sistema distribuido y facilitar el debugging, incorporamos **Logs estructurados** a través de la interfaz **SLF4J**. Para reducir el código repetitivo, utilizamos la anotación `@Slf4j` de **Lombok**.
A lo largo de los Controladores y Servicios, se registran eventos clave:
- `log.info(...)`: Para marcar el inicio de un proceso o recepciones HTTP exitosas.
- `log.warn(...)`: Para identificar validaciones de negocio fallidas.
- `log.error(...)`: Para capturar excepciones críticas o caídas de conexión externa.

---

## 🛤️ Diagrama de Flujo y Orquestación del Negocio

El siguiente diagrama representa el ciclo de vida de una atención estándar, reflejando cómo interactúan en secuencia los microservicios sin acoplarse directamente a las bases de datos ajenas:

```mermaid
graph TD
    A(["Ingreso Cliente"]) --> B("ms-persona (Validar Dueño)")
    B --> C("ms-equipo (Asignar Hardware)")
    C --> D("ms-ticket (Generar Orden de Soporte)")
    D --> E("ms-diagnostico (Evaluación Técnica)")
    E --> F{"¿Requiere Repuestos?"}
    F -- Sí --> G("ms-inventario (Descontar Stock vía Feign)")
    F -- No --> H("ms-pago (Liquidar Transacción)")
    G --> H
    H --> I(["ms-auditoria / ms-notificacion (Trazabilidad y Alertas)"])
    
    classDef default fill:#f9f9f9,stroke:#333,stroke-width:2px;
    classDef decision fill:#e1f5fe,stroke:#333,stroke-width:2px;
    class F decision;
```

---

## 🚀 Guía de Despliegue Local (Entorno de Desarrollo)

Para levantar el ecosistema completo desde IntelliJ IDEA:

1. **Requisitos del Sistema:** Instalar Java JDK 21, Apache Maven, IDE IntelliJ 2026.x y **Laragon**.
2. **Inicializar Motor SQL:** Iniciar los servicios de Laragon (asegurando que MySQL levante en el puerto `3306`).
3. **Creación de Esquemas:** Acceder al gestor de MySQL (ej. HeidiSQL incluido en Laragon) y ejecutar los scripts para crear las 9 bases de datos vacías:
    - `CREATE DATABASE fixnow_auth;`
    - `CREATE DATABASE fixnow_persona;`, etc.
4. **Boot Secuencial Fase 1 (Discovery):** Ejecutar como aplicación Spring Boot el módulo `ms-registry`. Aguardar a que inicie el servidor Tomcat en el puerto `8761`.
5. **Boot Secuencial Fase 2 (Core & Migrations):** Iniciar `ms-persona`, `ms-equipo`, e `ms-inventario`. Al levantar, se observarán los Logs de **Flyway** validando las tablas e insertando los datos iniciales.
6. **Boot Secuencial Fase 3 (Orchestration):** Levantar el resto de los módulos de negocio (`ms-ticket`, `ms-diagnostico`, `ms-pago`, `ms-auditoria`, `ms-notificacion`).
7. **Monitoreo:** Abrir el navegador en `http://localhost:8761/` y verificar en el Dashboard de Eureka que las instancias figuren en estado `UP`.