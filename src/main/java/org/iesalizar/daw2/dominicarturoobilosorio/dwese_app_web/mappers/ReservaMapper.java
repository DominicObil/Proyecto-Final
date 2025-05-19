package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.mappers;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ReservaCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ReservaDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Reserva;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.springframework.stereotype.Component;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.TurnoMesa;

@Component
public class ReservaMapper {

    public ReservaDTO toDTO(Reserva reserva) {
        return new ReservaDTO(
                reserva.getId(),
                reserva.getFechaReserva(),
                reserva.getHoraReserva(),
                reserva.getNumeroPersonas(),
                reserva.getComentarios(),
                reserva.getRestaurante().getId(),
                reserva.getUser().getId(),
                reserva.getTurno() != null ? reserva.getTurno().getId() : null, // nuevo
                reserva.getEstado().name(),
                reserva.getFechaCreacion()
        );
    }

    /**
     * Este método requiere que ya tengas cargados el User, Restaurante y TurnoMesa
     */
    public Reserva toEntity(ReservaCreateDTO dto, User user, Restaurante restaurante, TurnoMesa turno) {
        Reserva reserva = new Reserva();
        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setHoraReserva(dto.getHoraReserva());
        reserva.setNumeroPersonas(dto.getNumeroPersonas());
        reserva.setComentarios(dto.getComentarios());
        reserva.setUser(user);
        reserva.setRestaurante(restaurante);
        reserva.setTurno(turno); // 👈 importante
        return reserva;
    }

}
