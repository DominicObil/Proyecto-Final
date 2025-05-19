package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar por username (útil para login o autenticación)
    Optional<User> findByUsername(String username);

    // Validar si existe un usuario con cierto username
    boolean existsByUsername(String username);
}
