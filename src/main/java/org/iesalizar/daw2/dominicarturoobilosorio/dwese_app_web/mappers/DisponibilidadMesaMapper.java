package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.mappers;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.DisponibilidadMesaCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.DisponibilidadMesaDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.DisponibilidadMesa;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class DisponibilidadMesaMapper {

    public DisponibilidadMesaDTO toDTO(DisponibilidadMesa disponibilidad) {
        return new DisponibilidadMesaDTO(
                disponibilidad.getId(),
                disponibilidad.getRestaurante().getId(),
                disponibilidad.getFecha(),
                disponibilidad.getHoraInicio(),
                disponibilidad.getHoraFin(),
                disponibilidad.getMesasDisponibles()
        );
    }

    public DisponibilidadMesa toEntity(DisponibilidadMesaCreateDTO dto, Restaurante restaurante) {
        DisponibilidadMesa disponibilidad = new DisponibilidadMesa();
        disponibilidad.setFecha(dto.getFecha());
        disponibilidad.setHoraInicio(dto.getHoraInicio());
        disponibilidad.setHoraFin(dto.getHoraFin());
        disponibilidad.setMesasDisponibles(dto.getMesasDisponibles());
        disponibilidad.setRestaurante(restaurante); // ahora se le asigna correctamente
        return disponibilidad;
    }
}
