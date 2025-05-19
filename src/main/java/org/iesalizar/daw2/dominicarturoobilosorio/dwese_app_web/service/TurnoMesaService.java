package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.TurnoMesaCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.TurnoMesaDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.TurnoMesa;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.mappers.TurnoMesaMapper;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.RestauranteRepository;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.TurnoMesaRepository;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TurnoMesaService {

    private final TurnoMesaRepository turnoMesaRepository;
    private final RestauranteRepository restauranteRepository;
    private final TurnoMesaMapper turnoMesaMapper;
    private final MessageSource messageSource; // 👈 Añade esto

    public TurnoMesaDTO createTurno(TurnoMesaCreateDTO dto, Locale locale) {
        log.info("Creando turno para restaurante {}", dto.getRestauranteId());

        Restaurante restaurante = restauranteRepository.findById(dto.getRestauranteId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurante no encontrado"));

        TurnoMesa turno = turnoMesaMapper.toEntity(dto);
        turno.setRestaurante(restaurante);

        TurnoMesa saved = turnoMesaRepository.save(turno);
        return turnoMesaMapper.toDTO(saved);
    }

    public List<TurnoMesaDTO> getTurnosPorRestaurante(Long restauranteId) {
        return turnoMesaRepository.findByRestauranteId(restauranteId)
                .stream()
                .map(turnoMesaMapper::toDTO)
                .collect(Collectors.toList());
    }



    public Optional<TurnoMesaDTO> getById(Long id) {
        return turnoMesaRepository.findById(id).map(turnoMesaMapper::toDTO);
    }


    public TurnoMesaDTO updateTurno(Long id, TurnoMesaCreateDTO dto, Locale locale) {
        TurnoMesa turno = turnoMesaRepository.findById(id)
                .orElseThrow(() -> {
                    String error = messageSource.getMessage("msg.turno.notFound", null, locale);
                    log.warn("Turno no encontrado con ID {}", id);
                    return new IllegalArgumentException(error);
                });

        Restaurante restaurante = restauranteRepository.findById(dto.getRestauranteId())
                .orElseThrow(() -> {
                    String error = messageSource.getMessage("msg.restaurante.notFound", null, locale);
                    log.warn("Restaurante no encontrado con ID {}", dto.getRestauranteId());
                    return new IllegalArgumentException(error);
                });

        // Actualizamos los campos
        turno.setNombreTurno(dto.getNombreTurno());
        turno.setHoraInicio(dto.getHoraInicio());
        turno.setHoraFin(dto.getHoraFin());
        turno.setRestaurante(restaurante);

        TurnoMesa saved = turnoMesaRepository.save(turno);
        return turnoMesaMapper.toDTO(saved);
    }


    public void deleteTurno(Long id) {
        turnoMesaRepository.deleteById(id);
    }
}
