package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Notificacion;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByEnviadaFalseAndFechaEnvioBefore(LocalDateTime ahora);
}

