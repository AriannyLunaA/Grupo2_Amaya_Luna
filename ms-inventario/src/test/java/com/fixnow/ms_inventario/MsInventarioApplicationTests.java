package com.fixnow.ms_inventario;

import com.fixnow.ms_inventario.Model.Repuesto;
import com.fixnow.ms_inventario.Service.RepuestoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MsInventarioApplicationTests {

	@Autowired
	RepuestoService servicio;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("regla de negocio: el nombre del repuesto es obligatorio")
	void checkNombreNoNuloNiVacio() {
		Repuesto repuesto = servicio.buscarPorId(1);
		log.info("verificando nombre para el repuesto ID 1");
		assertNotNull(repuesto.getNombre(), "el nombre del repuesto no puede estar vacío");
	}

	@Test
	@DisplayName("regla de negocio: el stock no puede ser un número negativo")
	void checkStockNoNegativo() {
		Repuesto repuesto = servicio.buscarPorId(1);
		log.info("validando el stock para el repuesto: {}", repuesto.getNombre());
		assertNotNull(repuesto.getStock(), "el stock debe tener un valor asignado");
		assertTrue(repuesto.getStock() >= 0, "no se permite inventario con stock negativo");
	}

	@Test
	@DisplayName("regla de negocio: el precio unitario no puede ser negativo")
	void checkPrecioUnitarioValido() {
		Repuesto repuesto = servicio.buscarPorId(1);
		log.info("validando que el precio del repuesto sea mayor a 0");
		assertNotNull(repuesto.getPrecioUnitario(), "el precio unitario no puede estar vacío");
		assertTrue(repuesto.getPrecioUnitario() >= 0, "el precio de un repuesto jamás puede ser negativo");
	}

	@Test
	@DisplayName("regla de negocio: el inventario debe contener repuestos")
	void checkInventarioNoVacio() {
		List<Repuesto> catalogo = servicio.listarTodos();
		log.info("consultando inventario. Total registros: {}", catalogo.size());
		assertFalse(catalogo.isEmpty(), "el inventario debe tener al menos un registro");
	}
}
