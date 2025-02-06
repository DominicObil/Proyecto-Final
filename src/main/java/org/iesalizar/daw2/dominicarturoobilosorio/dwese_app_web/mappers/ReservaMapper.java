package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.mappers;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ReservaCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ReservaDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {

    public ReservaDTO toDTO(Reserva reserva) {
        return new ReservaDTO(
                reserva.getId(),
                reserva.getFechaReserva(),
                reserva.getHoraReserva(),
                reserva.getNumeroPersonas(),
                reserva.getComentarios(),
                reserva.getRestaurante().getId()
        );
    }

    public Reserva toEntity(ReservaCreateDTO dto) {
        Reserva reserva = new Reserva();
        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setHoraReserva(dto.getHoraReserva());
        reserva.setNumeroPersonas(dto.getNumeroPersonas());
        reserva.setComentarios(dto.getComentarios());
        return reserva;
    }
}
