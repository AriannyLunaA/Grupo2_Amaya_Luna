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

    @NotNull(message = "el id del ticket es obligatorio")
    @Positive(message = "el id del ticket debe ser un número positivo")
    @Column(name = "id_ticket", nullable = false)
    private long idTicket;


    @Column(name = "correo_destino", nullable = false, length = 100)
    private String correoDestino;

    @NotBlank(message = "el mensaje no puede estar vacío")
    @Column(name = "mensaje", nullable = false, length = 255)
    private String mensaje;

    @NotBlank(message = "el tipo de notificación es obligatorio")
    @Column(name = "tipo_notificacion", nullable = false, length = 50)
    private String tipoNotificacion;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;
}
