package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    // Consulta para verificar si existe un restaurante con un nombre dado.
    @Query("SELECT COUNT(r) > 0 FROM Restaurante r WHERE r.nombre = :nombre")
    boolean existsByNombre(@Param("nombre") String nombre);

    // Consulta para buscar restaurantes que contengan un término específico en su nombre (búsqueda parcial).
    @Query("SELECT r FROM Restaurante r WHERE LOWER(r.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Restaurante> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);

    // Consulta para encontrar todos los restaurantes con una capacidad mínima.
    @Query("SELECT r FROM Restaurante r WHERE r.capacidad >= :capacidadMinima")
    List<Restaurante> findByCapacidadGreaterThanEqual(@Param("capacidadMinima") Integer capacidadMinima);

    // Método para buscar un restaurante por su ID
    Optional<Restaurante> findById(Long id);

    // Nuevo método para verificar si existe un restaurante con el teléfono dado (puede ser útil)
    @Query("SELECT COUNT(r) > 0 FROM Restaurante r WHERE r.telefono = :telefono")
    boolean existsByTelefono(@Param("telefono") String telefono);

    // Método para encontrar todos los restaurantes por capacidad exacta (si es necesario)
    List<Restaurante> findByCapacidad(Integer capacidad);
}
