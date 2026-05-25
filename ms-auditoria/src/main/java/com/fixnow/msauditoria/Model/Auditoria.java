package com.fixnow.msauditoria.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad que representa el registro histórico de las auditorías del sistema.
 * Se mapea directamente con la estructura de Flyway (V1__crear_tabla_auditoria.sql).
 */
@Entity
@Table(name = "auditoria")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auditoria")
    private Integer idAuditoria;

    @Column(name = "id_ticket")
    private Integer idTicket;

    @Column(name = "id_pago")
    private Integer idPago;

    @Column(name = "id_persona")
    private Integer idPersona;

    @NotBlank(message = "La acción realizada es obligatoria")
    @Column(name = "accion", nullable = false, length = 100)
    private String accion;

    @NotBlank(message = "Los detalles son obligatorios")
    @Column(name = "detalles", nullable = false, length = 255)
    private String detalles;

    @NotNull(message = "La fecha es obligatoria")
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;
}
