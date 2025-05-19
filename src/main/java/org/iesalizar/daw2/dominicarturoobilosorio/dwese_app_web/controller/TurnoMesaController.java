package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.TurnoMesaCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.TurnoMesaDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service.TurnoMesaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/api/v1/turnos")
@RequiredArgsConstructor
public class TurnoMesaController {

    private final TurnoMesaService turnoMesaService;

    @PostMapping
    public ResponseEntity<TurnoMesaDTO> crearTurno(@Valid @RequestBody TurnoMesaCreateDTO dto, Locale locale) {
        TurnoMesaDTO result = turnoMesaService.createTurno(dto, locale);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<TurnoMesaDTO>> getTurnosPorRestaurante(@PathVariable Long restauranteId) {
        return ResponseEntity.ok(turnoMesaService.getTurnosPorRestaurante(restauranteId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoMesaDTO> getById(@PathVariable Long id) {
        return turnoMesaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurnoMesaDTO> updateTurno(@PathVariable Long id, @Valid @RequestBody TurnoMesaCreateDTO dto, Locale locale) {
        TurnoMesaDTO updated = turnoMesaService.updateTurno(id, dto, locale);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        turnoMesaService.deleteTurno(id);
        return ResponseEntity.noContent().build();
    }
}
