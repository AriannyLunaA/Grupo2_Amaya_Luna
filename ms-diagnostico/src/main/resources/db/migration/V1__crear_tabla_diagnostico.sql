CREATE TABLE diagnostico (
                             id_diagnostico BIGINT AUTO_INCREMENT PRIMARY KEY,
                             id_ticket BIGINT NOT NULL,
                             detalle_revision VARCHAR(255) NOT NULL,
                             necesita_repuesto BOOLEAN NOT NULL,
                             id_repuesto INT NULL,
                             cantidad_repuesto INT NULL,
                             costo_estimado INT NOT NULL,
                             tiempo_estimado_dias INT NOT NULL,
                             estado VARCHAR(50) NOT NULL
);

INSERT INTO diagnostico (id_ticket, detalle_revision, necesita_repuesto, id_repuesto, cantidad_repuesto, costo_estimado, tiempo_estimado_dias, estado)
VALUES (1, 'se requiere cambio de pasta térmica y limpieza de ventiladores', FALSE, NULL, NULL, 25000, 1, 'FINALIZADO');