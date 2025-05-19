package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalTime;

/**
 * DTO para crear turnos de mesas en un restaurante.
 *
 * Usado por los administradores para configurar los turnos disponibles en el sistema.
 */
@Data
@Schema(name = "TurnoMesaCreateDTO", description = "DTO para crear un turno de mesas en un restaurante.")
public class TurnoMesaCreateDTO {

    @NotNull(message = "{msg.turno.restauranteId.notNull}")
    @Schema(description = "ID del restaurante al que pertenece el turno.", example = "1")
    private Long restauranteId;

    @NotBlank(message = "{msg.turno.nombre.notBlank}")
    @Size(max = 50, message = "{msg.turno.nombre.size}")
    @Schema(description = "Nombre del turno (por ejemplo, 'Comida', 'Cena').", example = "Comida")
    private String nombreTurno;

    @NotNull(message = "{msg.turno.horaInicio.notNull}")
    @Schema(description = "Hora de inicio del turno.", example = "13:00:00")
    private LocalTime horaInicio;

    @NotNull(message = "{msg.turno.horaFin.notNull}")
    @Schema(description = "Hora de fin del turno.", example = "15:30:00")
    private LocalTime horaFin;
}
