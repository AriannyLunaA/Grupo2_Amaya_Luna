package com.fixnow.msdiagnostico.DTO;

import lombok.Data;

@Data
public class DiagnosticoDTO {
    private Long idDiagnostico;
    private Long idTicket;
    private String detalleRevision;
    private Boolean necesitaRepuesto;
    private Integer costoEstimado;
    private Integer tiempoEstimadoDias;
}
