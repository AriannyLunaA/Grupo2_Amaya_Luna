package com.fixnow.msdiagnostico;

import com.fixnow.msdiagnostico.Model.Diagnostico;
import com.fixnow.msdiagnostico.Repository.DiagnosticoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class DiagnosticoRepositoryTest {

    @Autowired
    DiagnosticoRepository diagnosticoRepository;

    @Test
    @DisplayName("Prueba 1: Validar persistencia exitosa")
    void prueba1_PersistenciaExitosa() {
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setIdTicket(100L);
        diagnostico.setDetalleRevision("Placa base dañada");
        diagnostico.setNecesitaRepuesto(true);
        diagnostico.setCostoEstimado(45000);
        diagnostico.setTiempoEstimadoDias(5);
        diagnostico.setEstado("INGRESADO");

        Diagnostico guardado = diagnosticoRepository.save(diagnostico);

        assertNotNull(guardado.getIdDiagnostico(), "El diagnóstico debe tener un ID asignado");
    }

    @Test
    @DisplayName("Prueba 2: Validar rechazo por campos obligatorios nulos")
    void prueba2_RechazoPorCamposNulos() {
        Diagnostico diagnosticoInvalido = new Diagnostico();
        diagnosticoInvalido.setIdTicket(102L);
        diagnosticoInvalido.setDetalleRevision(null);
        diagnosticoInvalido.setNecesitaRepuesto(false);
        diagnosticoInvalido.setCostoEstimado(0);
        diagnosticoInvalido.setTiempoEstimadoDias(0);

        assertThrows(Exception.class, () -> diagnosticoRepository.save(diagnosticoInvalido),
                "La base de datos debe rechazar un detalle de revisión nulo");
    }

    @Test
    @DisplayName("Prueba 3: Validar rechazo por exceder longitud permitida")
    void prueba3_RechazoPorLongitudExcedida() {
        Diagnostico diagnosticoInvalido = new Diagnostico();
        diagnosticoInvalido.setIdTicket(103L);
        diagnosticoInvalido.setDetalleRevision("Detalle ok");
        diagnosticoInvalido.setNecesitaRepuesto(false);
        diagnosticoInvalido.setCostoEstimado(0);
        diagnosticoInvalido.setTiempoEstimadoDias(0);
        diagnosticoInvalido.setEstado("Estado excedido en la cantidad de caracteres ******************************************");

        assertThrows(Exception.class, () -> diagnosticoRepository.save(diagnosticoInvalido),
                "El sistema debe rechazar estados que excedan el límite de caracteres");
    }
}