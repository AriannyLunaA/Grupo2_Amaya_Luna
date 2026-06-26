CREATE TABLE equipo (
                        id_equipo BIGINT AUTO_INCREMENT PRIMARY KEY,
                        id_persona BIGINT NOT NULL,
                        tipo VARCHAR(50) NOT NULL,
                        marca VARCHAR(50) NOT NULL,
                        modelo VARCHAR(100) NOT NULL,
                        procesador VARCHAR(100) NOT NULL,
                        memoria_ram VARCHAR(50) NOT NULL,
                        almacenamiento VARCHAR(100) NOT NULL,
                        tarjeta_grafica VARCHAR(100) NOT NULL,
                        numero_serie VARCHAR(100),
                        observaciones_fisicas VARCHAR(255)
);

INSERT INTO equipo (id_persona, tipo, marca, modelo, procesador, memoria_ram, almacenamiento, tarjeta_grafica, numero_serie, observaciones_fisicas)
VALUES (1, 'Notebook', 'HP', 'Victus 15', 'Intel Core i5', '16GB', '512GB SSD M.2', 'NVIDIA GTX 1650', 'SN-987654321', 'mucha lentitud');

INSERT INTO equipo (id_persona, tipo, marca, modelo, procesador, memoria_ram, almacenamiento, tarjeta_grafica, numero_serie, observaciones_fisicas)
VALUES (2, 'Notebook', 'Acer', 'Nitro 5', 'AMD Ryzen 5', '32GB', '512GB SSD', 'NVIDIA RTX 3060', 'SN-112233445', 'mucho polvo en los ventiladores');