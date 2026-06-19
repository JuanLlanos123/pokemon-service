package com.pokemon.controller;

import com.pokemon.service.EntrenadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entrenador/login")
@Tag(name = "Entrenadores", description = "Endpoints para la gestión de entrenadores y el control de capturas de Pokémon")
public class EntrenadorLoginController {

    private final EntrenadorService entrenadorService;

    public EntrenadorLoginController(EntrenadorService entrenadorService) {
        this.entrenadorService = entrenadorService;
    }

    public record LoginRequest(String email) {}
    public record LoginResponse(String uuid, String uiuid) {}

    @PostMapping
    @Operation(summary = "Iniciar sesión de entrenador", description = "Busca un entrenador registrado por su correo electrónico y retorna su UUID.")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        if (request == null || request.email() == null) {
            return ResponseEntity.badRequest().build();
        }
        return entrenadorService.findByEmail(request.email())
                .map(entrenador -> ResponseEntity.ok(new LoginResponse(
                        entrenador.getUuid().toString(),
                        entrenador.getUuid().toString()
                )))
                .orElse(ResponseEntity.notFound().build());
    }
}
