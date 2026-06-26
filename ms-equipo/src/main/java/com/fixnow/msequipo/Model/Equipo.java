package com.fixnow.msequipo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "equipo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo")
    private Long idEquipo;

    @NotNull(message = "El ID de la persona es obligatorio")
    @Column(name = "id_persona")
    private Long idPersona;

    @NotNull(message = "El tipo de equipo es obligatorio")
    @Size(max = 50)
    private String tipo;

    @NotNull(message = "La marca es obligatoria")
    @Size(max = 50)
    private String marca;

    @NotNull(message = "El modelo es obligatorio")
    @Size(max = 100)
    private String modelo;

    @NotNull(message = "El procesador es obligatorio")
    @Size(max = 100)
    private String procesador;

    @NotNull(message = "La memoria RAM es obligatoria")
    @Column(name = "memoria_ram")
    @Size(max = 50)
    private String memoriaRam;

    @NotNull(message = "El almacenamiento es obligatorio")
    @Size(max = 100)
    private String almacenamiento;

    @NotNull(message = "La tarjeta gráfica es obligatoria")
    @Column(name = "tarjeta_grafica")
    @Size(max = 100)
    private String tarjetaGrafica;

    @Column(name = "numero_serie")
    @Size(max = 100)
    private String numeroSerie;

    @Column(name = "observaciones_fisicas")
    @Size(max = 255)
    private String observacionesFisicas;
}