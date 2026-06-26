package com.fixnow.msequipo;

import com.fixnow.msequipo.DTO.EquipoDTO;
import com.fixnow.msequipo.Service.EquipoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MsEquipoApplicationTests {

	@Autowired
	EquipoService servicio;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Regla de Negocio: el equipo debe estar asignado a un ID de Persona")
	void checkDueñoAsignado() {
		EquipoDTO equipo = servicio.buscarPorId(1L);
		log.info("revisando asignación de la persona para el equipo con ID 1");
		assertNotNull(equipo.getIdPersona(), "el equipo no puede existir sin un ID de una persona asignada");
	}

	@Test
	@DisplayName("Regla de Negocio: la marca no pueden superar 50 caracteres y el modelo no pueden superar los 100 caracteres")
	void checkLimitesCaracteresMarcaModelo() {
		EquipoDTO equipo = servicio.buscarPorId(1L);
		log.info("verificando longitud de marca ({}) y modelo ({})", equipo.getMarca(), equipo.getModelo());
		assertTrue(equipo.getMarca().length() <= 50, "la marca excede el límite de 50 caracteres");
		assertTrue(equipo.getModelo().length() <= 100, "el modelo excede el límite de 100 caracteres");
	}

	@Test
	@DisplayName("Regla de Negocio: los componentes CPU, RAM y almacenamiento son obligatorios")
	void checkComponentesBasePresentes() {
		EquipoDTO equipo = servicio.buscarPorId(1L);
		log.info("revisando componentes CPU,RAM y almacenamiento del equipo");
		assertNotNull(equipo.getProcesador(), "el procesador es obligatorio");
		assertNotNull(equipo.getMemoriaRam(), "la memoria RAM es obligatoria");
		assertNotNull(equipo.getAlmacenamiento(), "el almacenamiento es obligatorio");
	}
}