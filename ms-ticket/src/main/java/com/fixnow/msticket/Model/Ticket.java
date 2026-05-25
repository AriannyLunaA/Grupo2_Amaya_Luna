package com.fixnow.msticket.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Entity
@Table(name = "ticket")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Long idTicket;

    @NotNull(message = "el id de la persona es obligatorio")
    @Column(name = "id_persona")
    private Long idPersona;

    @NotNull(message = "el id del equipo es obligatorio")
    @Column(name = "id_equipo")
    private Long idEquipo;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    @NotNull(message = "el estado es obligatorio")
    @Size(max = 50)
    private String estado;

    @NotNull(message = "la descripcion de la falla es obligatoria")
    @Size(max = 255)
    @Column(name = "descripcion_falla")
    private String descripcionFalla;
}
