-- Tabla: users
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    image VARCHAR(255),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_password_change_date TIMESTAMP
);

-- Tabla: roles
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- Tabla intermedia: user_roles
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Tabla: Restaurante
DROP TABLE IF EXISTS Restaurante;

CREATE TABLE restaurante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    capacidad INT NOT NULL
);

-- Tabla: Reserva
DROP TABLE IF EXISTS Reserva;

CREATE TABLE reserva (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_reserva DATE NOT NULL,
    hora_reserva TIME NOT NULL,
    numero_personas INT NOT NULL,
    comentarios VARCHAR(255),
    restaurante_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    estado ENUM('PENDIENTE', 'CONFIRMADA', 'CANCELADA') DEFAULT 'PENDIENTE',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (restaurante_id) REFERENCES Restaurante(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabla: DisponibilidadMesa
DROP TABLE IF EXISTS DisponibilidadMesa;

CREATE TABLE DisponibilidadMesa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    restaurante_id BIGINT NOT NULL,
    fecha DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    mesas_disponibles INT NOT NULL,
    FOREIGN KEY (restaurante_id) REFERENCES Restaurante(id) ON DELETE CASCADE
);

-- Tabla: TurnoMesa
DROP TABLE IF EXISTS TurnoMesa;

CREATE TABLE TurnoMesa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    restaurante_id BIGINT NOT NULL,
    nombre_turno VARCHAR(50) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    FOREIGN KEY (restaurante_id) REFERENCES Restaurante(id) ON DELETE CASCADE
);

-- Tabla: ListaEspera
DROP TABLE IF EXISTS ListaEspera;

CREATE TABLE ListaEspera (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    restaurante_id BIGINT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    numero_personas INT NOT NULL,
    estado ENUM('EN_ESPERA', 'ATENDIDO', 'CANCELADO') DEFAULT 'EN_ESPERA',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (restaurante_id) REFERENCES Restaurante(id)
);

-- Tabla: Notificacion
DROP TABLE IF EXISTS Notificacion;

CREATE TABLE Notificacion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    reserva_id BIGINT NOT NULL,
    tipo ENUM('CONFIRMACION', 'RECORDATORIO') NOT NULL,
    mensaje TEXT NOT NULL,
    enviada BOOLEAN DEFAULT FALSE,
    fecha_envio TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (reserva_id) REFERENCES Reserva(id)
);

-- Tabla: HistorialReservas
DROP TABLE IF EXISTS HistorialReservas;

CREATE TABLE HistorialReservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    reserva_id BIGINT NOT NULL,
    accion ENUM('CREADA', 'MODIFICADA', 'CANCELADA') NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (reserva_id) REFERENCES Reserva(id)
);
