package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ReservaCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ReservaDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Reserva;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.mappers.ReservaMapper;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.ReservaRepository;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.UserRepository;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service.ReservaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Controlador REST para gestionar reservas.
 * <p>Usa {@code @Operation} y {@code @ApiResponses} para documentar automáticamente en Swagger.</p>
 */
@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Reservas", description = "API para gestionar reservas en restaurantes")
public class ReservaController {

    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaMapper reservaMapper;

    /**
     * Obtiene todas las reservas con paginación.
     */
    @Operation(summary = "Obtener todas las reservas", description = "Devuelve una lista paginada de reservas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de reservas obtenida correctamente.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ReservaDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    @GetMapping
    public ResponseEntity<?> getAllReservas(@PageableDefault(size = 10, sort = "fechaReserva") Pageable pageable) {
        logger.info("Solicitando todas las reservas con paginación: página {}, tamaño {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<ReservaDTO> reservas = reservaService.getAllReservas(pageable);
            logger.info("Se han encontrado {} reservas en la página actual.", reservas.getTotalElements());
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            logger.error("Error al obtener la lista de reservas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las reservas.");
        }
    }



    /**
     * Obtiene una reserva por su ID.
     */
    @Operation(summary = "Obtener una reserva por ID", description = "Busca una reserva específica en la base de datos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada."),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getReservaById(@PathVariable Long id) {
        logger.info("Buscando reserva con ID {}", id);
        try {
            Optional<ReservaDTO> reservaDTO = Optional.ofNullable(reservaService.getReservaById(id));

            if (reservaDTO.isPresent()) {
                logger.info("Reserva encontrada con ID {}", id);
                return ResponseEntity.ok(reservaDTO.get());
            } else {
                logger.warn("No se encontró ninguna reserva con ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La reserva no existe.");
            }
        } catch (Exception e) {
            logger.error("Error al buscar la reserva con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar la reserva.");
        }
    }

    /**
     * Crea una nueva reserva.
     */
    @Operation(summary = "Crear una nueva reserva", description = "Permite a los usuarios registrar una reserva.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada exitosamente."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    @PostMapping
    public ResponseEntity<?> createReserva(@Valid @RequestBody ReservaCreateDTO reservaCreateDTO, Locale locale) {
        logger.info("Creando nueva reserva...");
        ReservaDTO createdReserva = reservaService.createReserva(reservaCreateDTO, locale);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReserva);
    }

    /**
     * Actualiza una reserva existente.
     */
    @Operation(summary = "Actualizar una reserva", description = "Modifica los datos de una reserva existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva actualizada correctamente."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReserva(@PathVariable Long id, @Valid @RequestBody ReservaCreateDTO reservaCreateDTO, Locale locale) {
        logger.info("Actualizando reserva con ID {}", id);
        ReservaDTO updatedReserva = reservaService.updateReserva(id, reservaCreateDTO, locale);
        return ResponseEntity.ok(updatedReserva);
    }

    /**
     * Elimina una reserva por su ID.
     */
    @Operation(summary = "Eliminar una reserva", description = "Elimina una reserva por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva eliminada correctamente."),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })



    @GetMapping("/mis")
    public ResponseEntity<List<ReservaDTO>> getMisReservas(Authentication auth) {
        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Reserva> reservas = reservaRepository.findByUser(user);
        return ResponseEntity.ok(reservas.stream().map(reservaMapper::toDTO).toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReserva(@PathVariable Long id, Authentication auth) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        String username = auth.getName();
        if (!reserva.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado");
        }

        reservaRepository.delete(reserva);
        return ResponseEntity.noContent().build();
    }

}



