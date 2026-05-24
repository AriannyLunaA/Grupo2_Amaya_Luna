package com.fixnow.mspagos.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad principal que mapea la tabla de pagos gestionada por Flyway.
 */
@Entity
@Table(name = "pagos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;

    @NotNull(message = "El ID del ticket es obligatorio")
    @Positive(message = "El ID del ticket debe ser un número positivo")
    @Column(name = "id_ticket", nullable = false)
    private Integer idTicket;

    @NotNull(message = "El monto total no puede ser nulo")
    @Positive(message = "El monto a pagar debe ser mayor a cero")
    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @NotBlank(message = "El método de pago es obligatorio")
    @Column(name = "metodo_pago", nullable = false, length = 50)
    private String metodoPago;

    @NotBlank(message = "El estado del pago es obligatorio")
    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @NotNull(message = "La fecha de pago es obligatoria")
    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;
}
