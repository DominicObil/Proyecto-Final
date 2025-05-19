package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.mappers;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.UserCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.UserDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getImage(),
                user.isEnabled()
        );
    }

    public User toEntity(UserCreateDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword()); // ⚠️ En el service deberás encriptar antes de guardar
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setImage(dto.getImage());
        user.setEnabled(true); // por defecto activado
        return user;
    }
}
