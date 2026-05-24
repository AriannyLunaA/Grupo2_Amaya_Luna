package com.fixnow.msnotificaciones.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad que representa el registro histórico de las notificaciones enviadas a los clientes.
 * Se mapea directamente con la estructura de Flyway (V1__crear_tabla_notificaciones.sql).
 */
@Entity
@Table(name = "notificaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Integer idNotificacion;

    @NotNull(message = "El ID del ticket es obligatorio")
    @Positive(message = "El ID del ticket debe ser un número positivo")
    @Column(name = "id_ticket", nullable = false)
    private Integer idTicket;

    @NotBlank(message = "El correo de destino es obligatorio")
    @Email(message = "El formato del correo no es válido")
    @Column(name = "correo_destino", nullable = false, length = 100)
    private String correoDestino;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Column(name = "mensaje", nullable = false, length = 255)
    private String mensaje;

    @NotBlank(message = "El tipo de notificación es obligatorio")
    @Column(name = "tipo_notificacion", nullable = false, length = 50)
    private String tipoNotificacion;

    @NotNull(message = "La fecha de envío es obligatoria")
    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;
}
