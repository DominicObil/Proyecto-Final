package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.enums.EstadoReserva;

@Data
public class EstadoReservaUpdateDTO {
    @NotNull
    private EstadoReserva estado;
}
