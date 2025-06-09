package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.SolicitudRestauranteDTO;
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

    // Listado sin paginación (opcional, por si lo usas en otro lado)
    public List<SolicitudRestaurante> listarTodas() {
        return repo.findAll();
    }

    // Listado paginado (este es el que usas en tu controller)
    public Page<SolicitudRestaurante> listarTodas(Pageable pageable) {
        return repo.findAll(pageable);
    }

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
}
