package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service;


import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Role;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Carga los detalles del usuario a partir de su nombre de usuario.
     *
     * @param username El nombre de usuario a buscar.
     * @return Un objeto UserDetails con la información de autenticación del usuario.
     * @throws UsernameNotFoundException Si el usuario no se encuentra en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Convierte los roles del usuario en GrantedAuthority
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(Role::getName)
                        .toList()
                        .toArray(new String[0]))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.isEnabled())
                .build();
    }
}
