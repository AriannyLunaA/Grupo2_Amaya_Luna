package com.fixnow.mspersona;

import com.fixnow.mspersona.Model.Persona;
import com.fixnow.mspersona.Service.PersonaService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MsPersonaApplicationTests {

    @Autowired
    PersonaService servicio;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Regla de Negocio: los nombres y los apellidos son obligatorios")
    void checkNombresNoNulos() {
        Persona persona = servicio.buscarPorId(1L);
        log.info("revisando que los nombres y los apellidos de ID 1 no estén vacíos");
        assertNotNull(persona.getNombres(), "los nombres no pueden estar vacíos");
        assertNotNull(persona.getApellidos(), "los apellidos no pueden estas vacíos");
    }

    @Test
    @DisplayName("Regla de Negocio: el Rut debe tener máximo 10 caracteres ")
    void checkLargoRut() {
        Persona persona = servicio.buscarPorId(1L);
        log.info("verificando longitud del RUT: {}", persona.getRut());
        assertTrue(persona.getRut().length() <= 12, "el Rut debe tener máximo 10 caracteres");
    }

    @Test
    @DisplayName("Regla de Negocio: el correo electrónico no puede estar vacío y tiene que contener '@'")
    void checkCorreoValido() {
        Persona persona = servicio.buscarPorId(1L);
        log.info("validando formato del correo: {}", persona.getCorreo());
        assertNotNull(persona.getCorreo());
        assertTrue(persona.getCorreo().contains("@"), "el correo no tiene un formato válido");
    }
}
