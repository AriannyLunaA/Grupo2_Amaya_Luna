package com.fixnow.ms_auth;

import com.fixnow.ms_auth.Service.AuthService;
import com.fixnow.ms_auth.Model.UsuarioCredencial;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MsAuthApplicationTests {

	@Autowired
	AuthService servicio;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Regla de Negocio: el nombre de usuario y Rol no pueden estar vacíos")
	void checkUsuarioRolValidos() {
		UsuarioCredencial usuario = servicio.listarTodos().get(0);
		log.info("Validando usuario: {}", usuario.getUsername());
		assertNotNull(usuario.getUsername(), "el username es obligatorio");
		assertNotNull(usuario.getRol(), "el rol asignado es obligatorio");
	}

	@Test
	@DisplayName("Regla de Negocio: la contraseña debe tener mínimo 25 carácteres")
	void checkLargoMinimoPassword() {
		UsuarioCredencial usuario = servicio.listarTodos().get(0);
		log.info("verificando longitud mínima de la contraseña");
		assertNotNull(usuario.getPassword());
		assertTrue(usuario.getPassword().length() >= 25, "la contraseña debe tener al menos 25 caracteres");
	}

	@Test
	@DisplayName("Integridad: obtención de usuarios del sistema")
	void checkListaUsuarios() {
		List<UsuarioCredencial> usuarios = servicio.listarTodos();
		log.info("Validando carga de la tabla de credenciales");
		assertFalse(usuarios.isEmpty(), "La base de datos de Auth debe contener al menos un usuario");
	}

	@Test
	@DisplayName("Regla de Negocio: el nombre de usuario tiene un máximo de 255 caracteres")
	void checkLargoMaximoUsername() {
		UsuarioCredencial usuario = servicio.listarTodos().get(0);
		log.info("verificando longitud máxima del username: {}", usuario.getUsername());
		assertTrue(usuario.getUsername().length() <= 255, "el username excede el límite de 255 caracteres permitido");
	}
}