package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.RegisterRequestDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Role;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.RestauranteRepository;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.RoleRepository;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Locale;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(RegisterRequestDTO dto) {
        // 1. Comprobar que el username no exista
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está registrado.");
        }

        // 2. Validar tipoUsuario
        String tipo = dto.getTipoUsuario().toUpperCase(Locale.ROOT);
        if (!(tipo.equals("USER") || tipo.equals("ADMIN"))) {
            throw new IllegalArgumentException("Tipo de usuario inválido (debe ser USER o ADMIN).");
        }

        // 3. Buscar el rol correspondiente
        String rolStr = tipo.equals("USER") ? "ROLE_USER" : "ROLE_ADMIN";
        Role rol = roleRepository.findByName(rolStr)
                .orElseThrow(() -> new IllegalArgumentException("El rol " + rolStr + " no existe en la base de datos."));

        // 4. Crear usuario y cifrar contraseña
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setImage(dto.getImage());
        user.setEnabled(true);
        user.setRoles(Collections.singleton(rol));

        // 5. Si es ADMIN, crear restaurante y (opcional) asociar restaurante al user
        if (tipo.equals("ADMIN")) {
            if (dto.getNombreRestaurante() == null || dto.getDireccionRestaurante() == null
                    || dto.getTelefonoRestaurante() == null || dto.getCapacidadRestaurante() == null) {
                throw new IllegalArgumentException("Para usuarios ADMIN, los datos del restaurante son obligatorios.");
            }
            Restaurante restaurante = new Restaurante(
                    dto.getNombreRestaurante(),
                    dto.getDireccionRestaurante(),
                    dto.getTelefonoRestaurante(),
                    dto.getCapacidadRestaurante()
            );
            restauranteRepository.save(restaurante);

            // (Opcional) Si quieres vincular restaurante <-> usuario, añade un campo en User y aquí
            // user.setRestaurante(restaurante);
        }

        // 6. Guardar usuario
        return userRepository.save(user);
    }
}
