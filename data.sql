-- Insertar datos en la tabla Restaurante
INSERT INTO restaurantes (nombre, direccion, telefono, capacidad) VALUES
('Restaurante La Mar', 'Calle Marina 123, Barcelona', '934567890', 50),
('Pizzería Bella Italia', 'Via Roma 45, Madrid', '914567891', 30),
('El Asador Argentino', 'Av. Libertador 88, Valencia', '964567892', 40);

-- Insertar datos en la tabla Reserva
INSERT INTO Reserva (fecha_reserva, hora_reserva, numero_personas, comentarios, restaurante_id) VALUES
('2024-12-05', '20:00:00', 4, 'Mesa cerca de la ventana, por favor', 1),
('2024-12-05', '21:00:00', 2, NULL, 1),
('2024-12-06', '13:30:00', 6, 'Celebración de cumpleaños', 2),
('2024-12-06', '19:00:00', 3, 'Mesa tranquila', 3),
('2024-12-07', '20:00:00', 5, NULL, 1);


-- Insertar datos de ejemplo para 'roles'
INSERT IGNORE INTO roles (id, name) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_MANAGER'),
(3, 'ROLE_USER');


-- Insertar datos de ejemplo para 'users'. La contraseña de cada usuario es password
INSERT IGNORE INTO users (id, username, password, enabled, first_name, last_name, image, created_date, last_modified_date, last_password_change_date) VALUES
(1, 'admin', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'Admin', 'User', '/images/admin.jpg', NOW(), NOW(), NOW()),
(2, 'manager', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'Manager', 'User', '/images/manager.jpg', NOW(), NOW(), NOW()),
(3, 'normal', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'Regular', 'User', '/images/user.jpg', NOW(), NOW(), NOW());


-- Asignar el rol de administrador al usuario con id 1
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(1, 1);
-- Asignar el rol de gestor al usuario con id 2
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(2, 2);
-- Asignar el rol de usuario normal al usuario con id 3
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(3, 3);

