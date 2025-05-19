package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.mappers;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.TurnoMesaCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.TurnoMesaDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.TurnoMesa;
import org.springframework.stereotype.Component;

@Component
public class TurnoMesaMapper {

    public TurnoMesaDTO toDTO(TurnoMesa turno) {
        return new TurnoMesaDTO(
                turno.getId(),
                turno.getRestaurante().getId(),
                turno.getNombreTurno(),
                turno.getHoraInicio(),
                turno.getHoraFin()
        );
    }

    public TurnoMesa toEntity(TurnoMesaCreateDTO dto) {
        TurnoMesa turno = new TurnoMesa();
        turno.setNombreTurno(dto.getNombreTurno());
        turno.setHoraInicio(dto.getHoraInicio());
        turno.setHoraFin(dto.getHoraFin());
        // El restaurante debe asignarse aparte
        return turno;
    }
}
