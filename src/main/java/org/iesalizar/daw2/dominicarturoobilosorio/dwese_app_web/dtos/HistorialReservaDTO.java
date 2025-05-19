package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialReservaDTO {
    private Long id;
    private Long userId;
    private Long reservaId;
    private String accion;
    private LocalDateTime fecha;
}

