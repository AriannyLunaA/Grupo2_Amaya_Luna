CREATE TABLE ticket (
                        id_ticket BIGINT AUTO_INCREMENT PRIMARY KEY,
                        id_persona BIGINT NOT NULL,
                        id_equipo BIGINT NOT NULL,
                        fecha_ingreso DATE NOT NULL,
                        estado VARCHAR(50) NOT NULL,
                        descripcion_falla VARCHAR(255) NOT NULL
);

-- Registros de prueba iniciales cruzados de forma lógica
INSERT INTO ticket (id_persona, id_equipo, fecha_ingreso, estado, descripcion_falla)
VALUES (1, 1, '2026-05-24', 'INGRESADO', 'limpieza por temperatura alta');

INSERT INTO ticket (id_persona, id_equipo, fecha_ingreso, estado, descripcion_falla)
VALUES (2, 2, '2026-05-23', 'EN_REVISION', 'no enciende');