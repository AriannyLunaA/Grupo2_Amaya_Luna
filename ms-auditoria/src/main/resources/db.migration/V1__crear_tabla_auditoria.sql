CREATE TABLE auditoria (
                           id_auditoria INT AUTO_INCREMENT PRIMARY KEY,
                           id_ticket INT NULL,
                           id_pago INT NULL,
                           id_persona INT NULL,
                           accion VARCHAR(100) NOT NULL, -- Ej: 'TICKET_CREADO', 'PAGO_PROCESADO', 'PERSONA_ELIMINADA'
                           detalles VARCHAR(255) NOT NULL,
                           fecha DATETIME NOT NULL
);

-- Insertamos datos de prueba
INSERT INTO auditoria (id_ticket, id_persona, accion, detalles, fecha)
VALUES (1, 15, 'TICKET_CREADO', 'Se registró un nuevo ticket en el sistema para diagnóstico.', NOW());

INSERT INTO auditoria (id_ticket, id_pago, accion, detalles, fecha)
VALUES (1, 1, 'PAGO_PROCESADO', 'Se procesó el pago asociado al ticket N° 1 exitosamente.', NOW());