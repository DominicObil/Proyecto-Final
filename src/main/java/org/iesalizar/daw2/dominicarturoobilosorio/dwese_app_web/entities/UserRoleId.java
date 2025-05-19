package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleId implements Serializable {
    private Long user;
    private Long role;
}
