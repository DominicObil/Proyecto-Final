package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaEsperaCreateDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Long restauranteId;

    @NotNull
    @FutureOrPresent
    private LocalDate fecha;

    @NotNull
    private LocalTime hora;

    @NotNull
    @Min(1)
    private Integer numeroPersonas;
}
