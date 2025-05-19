package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ListaEsperaCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ListaEsperaDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.*;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.*;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.mappers.ListaEsperaMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListaEsperaService {

    private final ListaEsperaRepository repository;
    private final UserRepository userRepository;
    private final RestauranteRepository restauranteRepository;
    private final ListaEsperaMapper mapper;

    public ListaEsperaDTO create(ListaEsperaCreateDTO dto, Locale locale) {
        log.info("Creando entrada en lista de espera");

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Restaurante restaurante = restauranteRepository.findById(dto.getRestauranteId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurante no encontrado"));

        ListaEspera entity = mapper.toEntity(dto, user, restaurante);
        ListaEspera saved = repository.save(entity);

        return mapper.toDTO(saved);
    }

    public List<ListaEsperaDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
