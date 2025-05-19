package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.DisponibilidadMesaCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.DisponibilidadMesaDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.DisponibilidadMesa;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.mappers.DisponibilidadMesaMapper;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.DisponibilidadMesaRepository;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.RestauranteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DisponibilidadMesaService {

    private final DisponibilidadMesaRepository disponibilidadMesaRepository;
    private final RestauranteRepository restauranteRepository;
    private final DisponibilidadMesaMapper mapper;

    public DisponibilidadMesaDTO create(DisponibilidadMesaCreateDTO dto, Locale locale) {
        Restaurante restaurante = restauranteRepository.findById(dto.getRestauranteId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurante no encontrado"));

        DisponibilidadMesa disponibilidad = mapper.toEntity(dto, restaurante);
        return mapper.toDTO(disponibilidadMesaRepository.save(disponibilidad));
    }

    public List<DisponibilidadMesaDTO> getAll() {
        return disponibilidadMesaRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<DisponibilidadMesaDTO> getByRestaurante(Long restauranteId) {
        return disponibilidadMesaRepository.findByRestauranteId(restauranteId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        disponibilidadMesaRepository.deleteById(id);
    }
}
