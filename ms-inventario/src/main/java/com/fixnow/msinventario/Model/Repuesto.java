package com.fixnow.msinventario.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_repuesto")
    private Integer idRepuesto;

    @NotBlank(message = "el nombre del repuesto es obligatorio")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @NotNull(message = "el stock es obligatorio")
    @Min(value = 0, message = "el stock no puede ser un número negativo")
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @NotNull(message = "el precio unitario es obligatorio")
    @Min(value = 0, message = "el precio unitario no puede ser negativo")
    @Column(name = "precio_unitario", nullable = false)
    private Integer precioUnitario;
}