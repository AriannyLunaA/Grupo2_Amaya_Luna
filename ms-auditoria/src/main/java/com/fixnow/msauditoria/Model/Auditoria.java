package com.fixnow.msauditoria.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


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
    private Long idTicket;

    @Column(name = "id_pago")
    private Integer idPago;

    @Column(name = "id_persona")
    private Long idPersona;

    @NotBlank(message = "la acción realizada es obligatoria")
    @Column(name = "accion", nullable = false, length = 100)
    private String accion;

    @NotBlank(message = "los detalles son obligatorios")
    @Column(name = "detalles", nullable = false, length = 255)
    private String detalles;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;
}
