package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnoMesaDTO {
    private Long id;
    private Long restauranteId;
    private String nombreTurno;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}
