package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.controller;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/debug")
public class DebugController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
