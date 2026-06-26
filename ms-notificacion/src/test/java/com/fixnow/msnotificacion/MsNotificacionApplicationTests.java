package com.fixnow.msnotificacion;

import com.fixnow.msnotificacion.Model.Notificacion;
import com.fixnow.msnotificacion.Service.NotificacionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MsNotificacionApplicationTests {

    @Autowired
    private NotificacionService notificacionService;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Regla de Negocio: El correo de destino debe tener un formato básico válido")
    void checkCorreoDestino() {
        Notificacion notificacion = notificacionService.buscarPorId(1L);
        log.info("Revisando regla de formato de correo para la notificacion ID 1: {}", notificacion.getCorreoDestino());

        // Verifica que el correo no sea nulo y contenga un @ como mínimo para que pueda ser enviado con éxito
        assertNotNull(notificacion.getCorreoDestino(), "Error: El correo de destino es nulo");
        assertTrue(notificacion.getCorreoDestino().contains("@"), "Error: El correo no tiene un formato valido (falta el @)");
    }

    @Test
    @DisplayName("Regla de Negocio: El mensaje a enviar es obligatorio y no puede estar vacío")
    void checkMensajeNoVacio() {
        Notificacion notificacion = notificacionService.buscarPorId(1L);
        log.info("Revisando regla de contenido del mensaje para la notificacion ID 1: {}", notificacion.getMensaje());

        // Verifica que el mensaje exista y tenga contenido real para que el cliente lo lea
        assertNotNull(notificacion.getMensaje(), "Error: El mensaje de la notificacion es nulo");
        assertFalse(notificacion.getMensaje().trim().isEmpty(), "Error: El mensaje de la notificacion esta en blanco");
    }

    @Test
    @DisplayName("Regla de Negocio: El tipo de notificación debe corresponder a un evento válido del sistema")
    void checkTipoNotificacionValido() {
        Notificacion notificacion = notificacionService.buscarPorId(1L);
        log.info("Revisando regla de tipo de evento para la notificacion ID 1: {}", notificacion.getTipoNotificacion());

        // Verifica que el motivo de la notificación sea uno de los permitidos por el flujo del negocio
        String tipo = notificacion.getTipoNotificacion();
        boolean tipoValido = tipo.equals("INGRESO") || tipo.equals("EN_PROCESO") ||
                tipo.equals("DIAGNOSTICO_FINALIZADO") || tipo.equals("PAGO_RECIBIDO") ||
                tipo.equals("EQUIPO_REPARADO");

        assertTrue(tipoValido, "Error: El tipo de notificacion no es reconocido por el flujo del sistema");
    }
}
