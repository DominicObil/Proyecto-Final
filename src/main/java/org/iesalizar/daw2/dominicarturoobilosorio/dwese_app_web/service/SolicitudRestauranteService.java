package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.SolicitudRestauranteDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.SolicitudRestauranteResponseDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.SolicitudRestaurante;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.RestauranteRepository;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.SolicitudRestauranteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudRestauranteService {

    @Autowired
    private SolicitudRestauranteRepository repo;
    @Autowired
    private RestauranteRepository restauranteRepo;

    // CREAR solicitud (igual que antes)
    public SolicitudRestaurante crearSolicitud(SolicitudRestauranteDTO dto, User owner) {
        SolicitudRestaurante solicitud = SolicitudRestaurante.builder()
                .nombre(dto.getNombre())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .capacidad(dto.getCapacidad())
                .owner(owner)
                .build();
        return repo.save(solicitud);
    }

    // Listado sin paginación, convertido a DTOs (opcional)
    public List<SolicitudRestauranteResponseDTO> listarTodas() {
        List<SolicitudRestaurante> solicitudes = repo.findAll();
        return solicitudes.stream()
                .map(this::toDTO)
                .toList();
    }

    // Listado paginado (este es el importante)
    public Page<SolicitudRestauranteResponseDTO> listarTodas(Pageable pageable) {
        Page<SolicitudRestaurante> solicitudes = repo.findAll(pageable);
        // Mapeamos las entidades a DTOs usando map de Page
        return solicitudes.map(this::toDTO);
    }

    // Aprobar solicitud (igual que antes)
    public Restaurante aprobarSolicitud(Long solicitudId) {
        SolicitudRestaurante solicitud = repo.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        Restaurante restaurante = Restaurante.builder()
                .nombre(solicitud.getNombre())
                .direccion(solicitud.getDireccion())
                .telefono(solicitud.getTelefono())
                .capacidad(solicitud.getCapacidad())
                .owner(solicitud.getOwner())
                .build();
        restauranteRepo.save(restaurante);
        repo.delete(solicitud);
        return restaurante;
    }

    // Método para convertir de entidad a DTO
    private SolicitudRestauranteResponseDTO toDTO(SolicitudRestaurante entity) {
        SolicitudRestauranteResponseDTO dto = new SolicitudRestauranteResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDireccion(entity.getDireccion());
        dto.setTelefono(entity.getTelefono());
        dto.setCapacidad(entity.getCapacidad());
        dto.setOwnerUsername(entity.getOwner().getUsername());
        dto.setOwnerId(entity.getOwner().getId());
        dto.setFechaSolicitud(entity.getFechaSolicitud());
        return dto;
    }
}
