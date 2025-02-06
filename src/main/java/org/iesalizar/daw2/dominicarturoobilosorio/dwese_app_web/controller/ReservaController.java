package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.controller;

import jakarta.validation.Valid;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ReservaCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ReservaDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service.ReservaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);

    @Autowired
    private ReservaService reservaService;

    /**
     * Obtiene todas las reservas.
     */
    @GetMapping
    public ResponseEntity<List<ReservaDTO>> getAllReservas() {
        logger.info("Obteniendo todas las reservas...");
        try {
            List<ReservaDTO> reservas = reservaService.getAllReservas();
            logger.info("Se encontraron {} reservas.", reservas.size());
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            logger.error("Error al obtener reservas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Obtiene una reserva por su ID.
     */
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
    @PostMapping
    public ResponseEntity<?> createReserva(@Valid @RequestBody ReservaCreateDTO reservaCreateDTO, Locale locale) {
        logger.info("Creando nueva reserva...");
        try {
            ReservaDTO createdReserva = reservaService.createReserva(reservaCreateDTO, locale);
            logger.info("Reserva creada con éxito.");
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReserva);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear la reserva: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al crear la reserva: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la reserva.");
        }
    }

    /**
     * Actualiza una reserva existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReserva(@PathVariable Long id, @Valid @RequestBody ReservaCreateDTO reservaCreateDTO, Locale locale) {
        logger.info("Actualizando reserva con ID {}", id);
        try {
            ReservaDTO updatedReserva = reservaService.updateReserva(id, reservaCreateDTO, locale);
            logger.info("Reserva con ID {} actualizada con éxito.", id);
            return ResponseEntity.ok(updatedReserva);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar la reserva con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar la reserva con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la reserva.");
        }
    }

    /**
     * Elimina una reserva por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReserva(@PathVariable Long id) {
        logger.info("Eliminando reserva con ID {}", id);
        try {
            reservaService.deleteReserva(id);
            logger.info("Reserva con ID {} eliminada exitosamente.", id);
            return ResponseEntity.ok("Reserva eliminada con éxito.");
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar la reserva con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar la reserva con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la reserva.");
        }
    }
}
