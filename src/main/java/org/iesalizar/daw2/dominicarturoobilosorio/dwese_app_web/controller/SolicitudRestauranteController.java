package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.SolicitudRestauranteDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.SolicitudRestauranteResponseDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service.SolicitudRestauranteService;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solicitudes-restaurante")
public class SolicitudRestauranteController {

    private static final Logger logger = LoggerFactory.getLogger(SolicitudRestauranteController.class);

    @Autowired
    private SolicitudRestauranteService solicitudService;
    @Autowired
    private UserRepository userRepository;

    // Endpoint para que el usuario cree una solicitud
    @PostMapping
    public ResponseEntity<?> crearSolicitud(@RequestBody SolicitudRestauranteDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        solicitudService.crearSolicitud(dto, owner);
        return ResponseEntity.ok("Solicitud enviada correctamente");
    }

    // Endpoint para que el admin vea todas las solicitudes (usando el DTO paginado)
    @Operation(
            summary = "Obtener todas las solicitudes de restaurante",
            description = "Devuelve una lista paginada de todas las solicitudes de restaurante registradas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitudes encontradas."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    @GetMapping("/solicitudes")
    public ResponseEntity<?> listarSolicitudes(
            @PageableDefault(size = 10, sort = "fechaSolicitud", direction = Sort.Direction.DESC) Pageable pageable) {
        logger.info("Solicitando todas las solicitudes de restaurante con paginación: página {}, tamaño {}",
                pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<SolicitudRestauranteResponseDTO> solicitudes = solicitudService.listarTodas(pageable);
            logger.info("Se han encontrado {} solicitudes en la página actual.", solicitudes.getTotalElements());
            return ResponseEntity.ok(solicitudes);
        } catch (Exception e) {
            logger.error("Error al obtener la lista de solicitudes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las solicitudes.");
        }
    }

    // Endpoint para que el admin apruebe (cree restaurante real)
    @PostMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobarSolicitud(@PathVariable Long id) {
        Restaurante restaurante = solicitudService.aprobarSolicitud(id);
        return ResponseEntity.ok("Restaurante creado correctamente con ID: " + restaurante.getId());
    }
}
