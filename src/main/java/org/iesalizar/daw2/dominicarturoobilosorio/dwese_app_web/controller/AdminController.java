package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.controller;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.UserCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Role;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.RoleRepository;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // SOLO ACCESIBLE PARA USUARIOS CON ROLE_ADMIN
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register-owner")
    public ResponseEntity<?> registerOwner(@RequestBody UserCreateDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya existe.");
        }
        // Busca el rol OWNER en la BBDD (o ROLE_ADMIN si quieres que sean admins)
        Role ownerRole = roleRepository.findByName("ROLE_OWNER")
                .orElseThrow(() -> new RuntimeException("No existe el rol OWNER"));

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setImage(dto.getImage());
        user.setEnabled(true);
        user.setRoles(Set.of(ownerRole));
        userRepository.save(user);

        return ResponseEntity.ok("Usuario dueño registrado correctamente.");
    }
}
