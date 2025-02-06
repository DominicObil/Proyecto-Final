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

