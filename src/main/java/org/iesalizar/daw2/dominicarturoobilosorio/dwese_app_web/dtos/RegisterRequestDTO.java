package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class RegisterRequestDTO {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String firstName;
        @NotBlank
        private String lastName;
        private String image;
        @NotBlank
        private String tipoUsuario; // "USER" o "ADMIN"
        // SOLO para admins
        private String nombreRestaurante;
        private String direccionRestaurante;
        private String telefonoRestaurante;
        private Integer capacidadRestaurante;

}
