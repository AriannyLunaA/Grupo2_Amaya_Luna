CREATE TABLE usuarios_credenciales (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       username VARCHAR(255) NOT NULL UNIQUE,
                                       password VARCHAR(255) NOT NULL,
                                       rol VARCHAR(50) NOT NULL,
                                       perfil_id BIGINT NOT NULL
);


INSERT INTO usuarios_credenciales (username, password, rol, perfil_id)
VALUES ('admin', '123', 'ADMIN', 1);

INSERT INTO usuarios_credenciales (username, password, rol, perfil_id)
VALUES ('tecnico1', '456', 'TECNICO', 1);
