-- Crear tabla Restaurante
CREATE TABLE Restaurante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    capacidad INT NOT NULL
);

-- Crear tabla Reserva
CREATE TABLE Reserva (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_reserva DATE NOT NULL,
    hora_reserva TIME NOT NULL,
    numero_personas INT NOT NULL,
    comentarios VARCHAR(255),
    restaurante_id BIGINT NOT NULL,
    FOREIGN KEY (restaurante_id) REFERENCES Restaurante(id) ON DELETE CASCADE
);
