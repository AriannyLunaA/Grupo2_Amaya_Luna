package com.fixnow.ms_inventario.ms_inventario;

import com.fixnow.ms_inventario.Model.Repuesto;
import com.fixnow.ms_inventario.Repository.RepuestoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties") // Uso h2
public class RepuestoRepositoryTest {
    @Autowired
    private RepuestoRepository repuestoRepository;

    @Test
    @DisplayName("Prueba 1: Guardar un repuesto exitosamente.")
    void deberiaGuardarUnRepuestoExitosamente() {
        //AAA Arrange, Act, Assert.
        // Arrange // preparando el escenario
        Repuesto repuesto = new Repuesto();
        repuesto.setNombre("Pastillas de Freno");
        repuesto.setDescripcion("Pastillas delanteras cerámicas");
        repuesto.setStock(15);
        repuesto.setPrecioUnitario(25000);

        // Act //Actuar Ejecutar metodo. logica que quiero probar
        Repuesto repuestoGuardado = repuestoRepository.save(repuesto);

        // Assert //Verificar  validar resultado, debe ser el esperado.
        assertNotNull(repuestoGuardado.getIdRepuesto(), "El ID no debería ser nulo tras guardar");
        assertEquals("Pastillas de Freno", repuestoGuardado.getNombre());

        Optional<Repuesto> encontrado = repuestoRepository.findById(repuestoGuardado.getIdRepuesto());
        assertTrue(encontrado.isPresent(), "El repuesto debería existir en la base de datos H2");


    }

    @Test
    @DisplayName("Prueba 2: Lanzar excepción si el nombre está en blanco")
    void LanzarExcepciónSiElNombreEstaEnBlanco() {
        //Arrange //Se fuerza el error dejando el nombre ´vacío.

        Repuesto repuestoInvalido = new Repuesto();
        repuestoInvalido.setNombre("");
        repuestoInvalido.setDescripcion("Filtro alternativo");
        repuestoInvalido.setStock(10);
        repuestoInvalido.setPrecioUnitario(8000);

        // Act & Assert // Validamos que el repositorio falle de forma controlada al intentar guardar
        assertThrows(Exception.class, () -> {
            repuestoRepository.save(repuestoInvalido);
        }, "Se esperaba que la base de datos rechazara el nombre en blanco");
    }

    @Test
    @DisplayName("Prueba 3: Lanzar excepción si el stock es negativo")
    void LanzarExcepcionSiStockEsNegativo() {
        // Arrange // Rompemos la regla Min(0) usando un número negativo
        Repuesto repuestoInvalido = new Repuesto();
        repuestoInvalido.setNombre("Bujía Incandescente");
        repuestoInvalido.setDescripcion("Bujía diésel");
        repuestoInvalido.setStock(-1);
        repuestoInvalido.setPrecioUnitario(15000);

        // Act & Assert
        assertThrows(Exception.class, () -> {
            repuestoRepository.save(repuestoInvalido);
        }, "Se esperaba que la base de datos rechazara un stock menor a 0");
    }


}//FIn
