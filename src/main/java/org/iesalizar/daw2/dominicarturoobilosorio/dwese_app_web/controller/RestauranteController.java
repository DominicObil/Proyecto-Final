package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.RestauranteCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.RestauranteDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service.RestauranteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

/**
 * Controlador REST para la gestión de restaurantes.
 */
@RestController
@RequestMapping("/api/restaurantes")
@Tag(name = "Restaurantes", description = "API para gestionar restaurantes")
public class RestauranteController {

    private static final Logger logger = LoggerFactory.getLogger(RestauranteController.class);

    @Autowired
    private RestauranteService restauranteService;

    /**
     * Obtiene la lista de todos los restaurantes con paginación.
     */
    @Operation(summary = "Obtener todos los restaurantes", description = "Devuelve una lista paginada de restaurantes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de restaurantes recuperada exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestauranteDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping
    public ResponseEntity<?> getAllRestaurantes(@PageableDefault(size = 10, sort = "nombre") Pageable pageable) {
        logger.info("Solicitando todos los restaurantes con paginación: página {}, tamaño {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<RestauranteDTO> restaurantes = restauranteService.getAllRestaurantes(pageable);
            logger.info("Se han encontrado {} restaurantes en la página actual.", restaurantes.getTotalElements());
            return ResponseEntity.ok(restaurantes);
        } catch (Exception e) {
            logger.error("Error al obtener la lista de restaurantes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los restaurantes.");
        }
    }



    /**
     * Obtiene un restaurante por su ID.
     */
    @Operation(summary = "Obtener un restaurante por ID", description = "Busca un restaurante específico en la base de datos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado."),
            @ApiResponse(responseCode = "404", description = "Restaurante no encontrado."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
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
    @Operation(summary = "Crear un nuevo restaurante", description = "Permite a los usuarios registrar un restaurante.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Restaurante creado exitosamente."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    @PostMapping
    public ResponseEntity<?> createRestaurante(@Valid @RequestBody RestauranteCreateDTO restauranteCreateDTO, Locale locale) {
        RestauranteDTO createdRestaurante = restauranteService.createRestaurante(restauranteCreateDTO, locale);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurante);
    }

    /**
     * Actualiza un restaurante existente.
     */
    @Operation(summary = "Actualizar un restaurante", description = "Modifica los datos de un restaurante existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante actualizado correctamente."),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
            @ApiResponse(responseCode = "404", description = "Restaurante no encontrado."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurante(@PathVariable Long id, @Valid @RequestBody RestauranteCreateDTO restauranteCreateDTO, Locale locale) {
        RestauranteDTO updatedRestaurante = restauranteService.updateRestaurante(id, restauranteCreateDTO, locale);
        return ResponseEntity.ok(updatedRestaurante);
    }

    /**
     * Elimina un restaurante por su ID.
     */
    @Operation(summary = "Eliminar un restaurante", description = "Elimina un restaurante por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante eliminado correctamente."),
            @ApiResponse(responseCode = "404", description = "Restaurante no encontrado."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurante(@PathVariable Long id) {
        restauranteService.deleteRestaurante(id);
        return ResponseEntity.ok("Restaurante eliminado con éxito.");
    }
}
