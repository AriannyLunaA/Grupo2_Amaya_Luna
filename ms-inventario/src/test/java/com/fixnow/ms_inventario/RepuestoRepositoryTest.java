package com.fixnow.ms_inventario;

import com.fixnow.ms_inventario.Model.Repuesto;
import com.fixnow.ms_inventario.Repository.RepuestoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class RepuestoRepositoryTest {

    @Autowired
    RepuestoRepository repuestoRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Integridad: guardar un repuesto exitosamente")
    void checkGuardarRepuesto() {
        Repuesto repuesto = new Repuesto();
        repuesto.setNombre("Pastillas de Freno");
        repuesto.setDescripcion("Pastillas delanteras cerámicas");
        repuesto.setStock(15);
        repuesto.setPrecioUnitario(25000);

        log.info("Validando inserción de repuesto: {}", repuesto.getNombre());
        Repuesto repuestoGuardado = repuestoRepository.save(repuesto);

        assertNotNull(repuestoGuardado.getIdRepuesto(), "el ID asignado es obligatorio tras la persistencia");
        assertEquals("Pastillas de Freno", repuestoGuardado.getNombre());
    }

    @Test
    @DisplayName("Regla de Negocio: el nombre del repuesto no puede estar en blanco")
    void checkValidacionNombreBlanco() {
        Repuesto repuestoInvalido = new Repuesto();
        repuestoInvalido.setNombre("");
        repuestoInvalido.setDescripcion("Filtro alternativo");
        repuestoInvalido.setStock(10);
        repuestoInvalido.setPrecioUnitario(8000);

        log.info("Verificando rechazo de persistencia con nombre vacío");
        assertThrows(Exception.class, () -> {
            repuestoRepository.save(repuestoInvalido);
        }, "la base de datos debe rechazar un nombre en blanco");
    }

    @Test
    @DisplayName("Regla de Negocio: el stock no puede ser un valor negativo")
    void checkValidacionStockNegativo() {
        Repuesto repuestoInvalido = new Repuesto();
        repuestoInvalido.setNombre("Bujía Incandescente");
        repuestoInvalido.setDescripcion("Bujía diésel");
        repuestoInvalido.setStock(-1);
        repuestoInvalido.setPrecioUnitario(15000);

        log.info("Verificando rechazo de persistencia con stock negativo");
        assertThrows(Exception.class, () -> {
            repuestoRepository.save(repuestoInvalido);
        }, "la base de datos debe rechazar un stock menor a 0");
    }
}