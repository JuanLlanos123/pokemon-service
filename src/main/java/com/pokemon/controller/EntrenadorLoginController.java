package com.pokemon.controller;

import com.pokemon.entity.Pokemon;
import com.pokemon.service.EntrenadorService;
import com.pokemon.service.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller raíz en /entrenador (sin prefijo /api).
 * Expone:
 *   POST /entrenador/login          → obtener UUID por correo
 *   GET  /entrenador/{uuid}/pokemons → listar pokémones del entrenador
 */
@RestController
@RequestMapping("/entrenador")
@Tag(name = "Entrenadores", description = "Endpoints para la gestión de entrenadores y el control de capturas de Pokémon")
public class EntrenadorLoginController {

    private final EntrenadorService entrenadorService;
    private final PokemonService    pokemonService;

    public EntrenadorLoginController(EntrenadorService entrenadorService,
                                     PokemonService pokemonService) {
        this.entrenadorService = entrenadorService;
        this.pokemonService    = pokemonService;
    }

    // ─── DTOs ─────────────────────────────────────────────────────────────────

    public record LoginRequest(String email) {}
    public record LoginResponse(String uuid, String uiuid) {}

    /**
     * DTO de respuesta reducido para pokémones de un entrenador.
     * Solo expone uuid, nombre y descripcion según la especificación.
     */
    public record PokemonResumenResponse(String uuid, String nombre, String descripcion) {}

    // ─── POST /entrenador/login ───────────────────────────────────────────────

    @PostMapping("/login")
    @Operation(
            summary = "Login de entrenador",
            description = "Recibe el correo electrónico de un entrenador registrado y retorna su UUID. " +
                          "Request: `{ \"email\": \"ash@pokemon.com\" }` → Response: `{ \"uuid\": \"...\", \"uiuid\": \"...\" }`")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        if (request == null || request.email() == null) {
            return ResponseEntity.badRequest().build();
        }
        return entrenadorService.findByEmail(request.email())
                .map(e -> ResponseEntity.ok(new LoginResponse(
                        e.getUuid().toString(),
                        e.getUuid().toString()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    // ─── GET /entrenador/{uuid}/pokemons ──────────────────────────────────────

    @GetMapping("/{uuid}/pokemons")
    @Operation(
            summary = "Pokémones de un entrenador",
            description = "Busca el entrenador por UUID y devuelve todos sus pokémones capturados. " +
                          "Realiza un JOIN triple: entrenador → pokemon_captura → pokemon. " +
                          "Respuesta: arreglo con `uuid`, `nombre` y `descripcion` de cada pokémon.")
    public ResponseEntity<List<PokemonResumenResponse>> pokemonesDelEntrenador(@PathVariable UUID uuid) {
        // Verificar que el entrenador existe
        if (entrenadorService.findByUuid(uuid).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<PokemonResumenResponse> pokemons = pokemonService.findByEntrenadorUuid(uuid)
                .stream()
                .map(p -> new PokemonResumenResponse(
                        p.getUuid().toString(),
                        p.getNombre(),
                        p.getDescripcion()
                ))
                .toList();

        return ResponseEntity.ok(pokemons);
    }
}
