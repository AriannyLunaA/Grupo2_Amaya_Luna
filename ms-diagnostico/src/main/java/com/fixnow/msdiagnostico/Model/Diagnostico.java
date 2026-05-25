package com.fixnow.msdiagnostico.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "diagnostico")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_diagnostico")
    private Long idDiagnostico;

    @NotNull(message = "el id del ticket es obligatorio")
    @Column(name = "id_ticket")
    private Long idTicket;

    @NotNull(message = "el detalle de la revisión es obligatorio")
    @Size(max = 255)
    @Column(name = "detalle_revision")
    private String detalleRevision;

    @NotNull
    @Column(name = "necesita_repuesto")
    private Boolean necesitaRepuesto;

    @NotNull
    @Column(name = "costo_estimado")
    private Integer costoEstimado;

    @NotNull
    @Column(name = "tiempo_estimado_dias")
    private Integer tiempoEstimadoDias;
}
