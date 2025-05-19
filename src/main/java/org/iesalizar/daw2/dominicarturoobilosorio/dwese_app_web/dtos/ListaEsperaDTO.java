package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListaEsperaDTO {
    private Long id;
    private Long userId;
    private Long restauranteId;
    private LocalDate fecha;
    private LocalTime hora;
    private Integer numeroPersonas;
    private String estado;
    private LocalDateTime fechaCreacion;
}
