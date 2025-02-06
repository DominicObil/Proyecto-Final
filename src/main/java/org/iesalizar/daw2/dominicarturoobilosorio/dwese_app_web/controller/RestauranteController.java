package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.controller;

import jakarta.validation.Valid;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.RestauranteCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.RestauranteDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service.RestauranteService;
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
@RequestMapping("/api/restaurantes")
public class RestauranteController {

    private static final Logger logger = LoggerFactory.getLogger(RestauranteController.class);

    @Autowired
    private RestauranteService restauranteService;

    /**
     * Obtiene la lista de todos los restaurantes.
     */
    @GetMapping
    public ResponseEntity<List<RestauranteDTO>> getAllRestaurantes() {
        logger.info("Obteniendo todos los restaurantes...");
        try {
            List<RestauranteDTO> restaurantes = restauranteService.getAllRestaurantes();
            logger.info("Se encontraron {} restaurantes.", restaurantes.size());
            return ResponseEntity.ok(restaurantes);
        } catch (Exception e) {
            logger.error("Error al obtener los restaurantes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Obtiene un restaurante por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getRestauranteById(@PathVariable Long id) {
        logger.info("Buscando restaurante con ID {}", id);
        try {
            Optional<RestauranteDTO> restauranteDTO = Optional.ofNullable(restauranteService.getRestauranteById(id));

            if (restauranteDTO.isPresent()) {
                logger.info("Restaurante encontrado con ID {}", id);
                return ResponseEntity.ok(restauranteDTO.get());
            } else {
                logger.warn("No se encontró ningún restaurante con ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El restaurante no existe.");
            }
        } catch (Exception e) {
            logger.error("Error al buscar el restaurante con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el restaurante.");
        }
    }

    /**
     * Crea un nuevo restaurante.
     */
    @PostMapping
    public ResponseEntity<?> createRestaurante(@Valid @RequestBody RestauranteCreateDTO restauranteCreateDTO, Locale locale) {
        logger.info("Creando nuevo restaurante...");
        try {
            RestauranteDTO createdRestaurante = restauranteService.createRestaurante(restauranteCreateDTO, locale);
            logger.info("Restaurante creado con éxito.");
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurante);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear el restaurante: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al crear el restaurante: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el restaurante.");
        }
    }

    /**
     * Actualiza un restaurante existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurante(@PathVariable Long id, @Valid @RequestBody RestauranteCreateDTO restauranteCreateDTO, Locale locale) {
        logger.info("Actualizando restaurante con ID {}", id);
        try {
            RestauranteDTO updatedRestaurante = restauranteService.updateRestaurante(id, restauranteCreateDTO, locale);
            logger.info("Restaurante con ID {} actualizado con éxito.", id);
            return ResponseEntity.ok(updatedRestaurante);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar el restaurante con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar el restaurante con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el restaurante.");
        }
    }

    /**
     * Elimina un restaurante por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurante(@PathVariable Long id) {
        logger.info("Eliminando restaurante con ID {}", id);
        try {
            restauranteService.deleteRestaurante(id);
            logger.info("Restaurante con ID {} eliminado exitosamente.", id);
            return ResponseEntity.ok("Restaurante eliminado con éxito.");
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar el restaurante con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar el restaurante con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el restaurante.");
        }
    }

}
