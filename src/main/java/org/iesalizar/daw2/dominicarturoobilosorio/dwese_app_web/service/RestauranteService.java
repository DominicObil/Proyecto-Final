package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.RestauranteCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.RestauranteDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.mappers.RestauranteMapper;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.RestauranteRepository;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class RestauranteService {

    private static final Logger logger = LoggerFactory.getLogger(RestauranteService.class);

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestauranteMapper restauranteMapper;

    @Autowired
    private MessageSource messageSource;

    /**
     * Obtiene todas los restaurantes con paginación y los convierte en una página de RestauranteDTO.
     *
     * @param pageable Objeto de paginación que define la página, el tamaño y la ordenación.
     * @return Página de RestauranteDTO.
     */
    public Page<RestauranteDTO> getAllRestaurantes(Pageable pageable) {
        logger.info("Solicitando todos los restaurantes con paginación: página {}, tamaño {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Restaurante> restaurantes = restauranteRepository.findAll(pageable);
            logger.info("Se han encontrado {} restaurantes en la página actual.", restaurantes.getNumberOfElements());
            return restaurantes.map(restauranteMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al obtener la lista de restaurantes: {}", e.getMessage());
            throw new RuntimeException("Error al obtener los restaurantes", e);
        }
    }

    public List<RestauranteDTO> getMisRestaurantes(User owner) {
        List<org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante> restaurantes = restauranteRepository.findByOwner(owner);
        return restaurantes.stream()
                .map(restauranteMapper::toDTO)
                .toList();

}

    /**
     * Obtiene un restaurante por su ID y lo convierte en un RestauranteDTO.
     *
     * @param id Identificador único del restaurante.
     * @return RestauranteDTO del restaurante encontrado.
     * @throws IllegalArgumentException Si el restaurante no existe.
     */
    public RestauranteDTO getRestauranteById(Long id) {
        logger.info("Buscando restaurante con ID {}", id);
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró el restaurante con ID {}", id);
                    return new IllegalArgumentException("El restaurante no existe.");
                });
        logger.info("Restaurante con ID {} encontrado.", id);
        return restauranteMapper.toDTO(restaurante);
    }

    /**
     * Crea un nuevo restaurante en la base de datos.
     *
     * @param restauranteCreateDTO DTO que contiene los datos del restaurante a crear.
     * @param locale Idioma para los mensajes de error.
     * @return DTO del restaurante creado.
     * @throws IllegalArgumentException Si ya existe un restaurante con el mismo nombre.
     */
    public RestauranteDTO createRestaurante(RestauranteCreateDTO restauranteCreateDTO, Locale locale) {
        logger.info("Creando un nuevo restaurante con nombre {}", restauranteCreateDTO.getNombre());

        // Validar que NO exista un restaurante con ese nombre
        if (restauranteRepository.existsByNombre(restauranteCreateDTO.getNombre())) {
            String errorMessage = messageSource.getMessage("msg.restaurant-controller.insert.nameExist", null, locale);
            logger.warn("Error al crear restaurante: {}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        // Buscar el propietario/owner
        User owner = userRepository.findById(restauranteCreateDTO.getOwnerId())
                .orElseThrow(() -> {
                    String msg = messageSource.getMessage("msg.restaurant-controller.insert.ownerNotFound", null, locale);
                    logger.warn("Error al crear restaurante: {}", msg);
                    return new IllegalArgumentException(msg);
                });

        // Crear la entidad Restaurante usando el mapper actualizado
        Restaurante restaurante = restauranteMapper.toEntity(restauranteCreateDTO, owner);

        // Guardar en la base de datos
        Restaurante savedRestaurante = restauranteRepository.save(restaurante);

        logger.info("Restaurante creado exitosamente con ID {}", savedRestaurante.getId());

        // Devolver el DTO
        return restauranteMapper.toDTO(savedRestaurante);
    }


    /**
     * Actualiza un restaurante existente en la base de datos.
     *
     * @param id Identificador del restaurante a actualizar.
     * @param restauranteCreateDTO DTO que contiene los nuevos datos del restaurante.
     * @param locale Idioma para los mensajes de error.
     * @return DTO del restaurante actualizado.
     * @throws IllegalArgumentException Si el restaurante no existe o si el nombre ya está en uso.
     */
    public RestauranteDTO updateRestaurante(Long id, RestauranteCreateDTO restauranteCreateDTO, Locale locale) {
        logger.info("Actualizando restaurante con ID {}", id);
        Restaurante existingRestaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró el restaurante con ID {}", id);
                    return new IllegalArgumentException("El restaurante no existe.");
                });

        if (restauranteRepository.existsByNombre(restauranteCreateDTO.getNombre()) &&
                !existingRestaurante.getNombre().equals(restauranteCreateDTO.getNombre())) {
            String errorMessage = messageSource.getMessage("msg.restaurant-controller.update.nameExist", null, locale);
            logger.warn("Error al actualizar restaurante: {}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        existingRestaurante.setNombre(restauranteCreateDTO.getNombre());
        existingRestaurante.setDireccion(restauranteCreateDTO.getDireccion());
        existingRestaurante.setTelefono(restauranteCreateDTO.getTelefono());
        existingRestaurante.setCapacidad(restauranteCreateDTO.getCapacidad());

        Restaurante updatedRestaurante = restauranteRepository.save(existingRestaurante);
        logger.info("Restaurante con ID {} actualizado exitosamente.", id);
        return restauranteMapper.toDTO(updatedRestaurante);
    }

    /**
     * Elimina un restaurante específico por su ID.
     *
     * @param id Identificador único del restaurante.
     * @throws IllegalArgumentException Si el restaurante no existe.
     */
    public void deleteRestaurante(Long id) {
        logger.info("Buscando restaurante con ID {}", id);
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró el restaurante con ID {}", id);
                    return new IllegalArgumentException("El restaurante no existe.");
                });

        restauranteRepository.deleteById(id);
        logger.info("Restaurante con ID {} eliminado exitosamente.", id);
    }
}
