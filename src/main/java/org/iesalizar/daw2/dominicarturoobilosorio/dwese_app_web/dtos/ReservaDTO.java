package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
    private Long id;
    private LocalDate fechaReserva;
    private LocalTime horaReserva;
    private Integer numeroPersonas;
    private String comentarios;
    private Long restauranteId;
    private Long userId;
    private Long turnoId;
    private String estado;
    private LocalDateTime fechaCreacion;
}
