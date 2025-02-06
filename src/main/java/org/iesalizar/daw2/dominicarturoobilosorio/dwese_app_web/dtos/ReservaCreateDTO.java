package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservaCreateDTO {
    @NotNull(message = "{msg.reserva.fecha.notNull}")
    private LocalDate fechaReserva;

    @NotNull(message = "{msg.reserva.hora.notNull}")
    private LocalTime horaReserva;

    @NotNull(message = "{msg.reserva.personas.notNull}")
    @Positive(message = "{msg.reserva.personas.positive}")
    private Integer numeroPersonas;

    @Size(max = 255, message = "{msg.reserva.comentarios.size}")
    private String comentarios;

    @NotNull(message = "{msg.reserva.restaurante.notNull}")
    private Long restauranteId;
}
