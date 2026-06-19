package com.pokemon.controller;

import com.pokemon.entity.Pokemon;
import com.pokemon.service.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pokemons")
@Tag(name = "Pokémon", description = "Endpoints para la gestión del catálogo de Pokémon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los Pokémon", description = "Retorna una lista de todos los Pokémon registrados.")
    public ResponseEntity<List<Pokemon>> obtenerTodos() {
        return ResponseEntity.ok(pokemonService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un Pokémon por su ID", description = "Busca y retorna un Pokémon específico por su identificador numérico.")
    public ResponseEntity<Pokemon> obtenerPorId(@PathVariable Integer id) {
        return pokemonService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Obtener un Pokémon por su UUID", description = "Busca y retorna un Pokémon específico por su identificador UUID único.")
    public ResponseEntity<Pokemon> obtenerPorUuid(@PathVariable UUID uuid) {
        return pokemonService.findByUuid(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo Pokémon", description = "Registra un nuevo Pokémon en el catálogo.")
    public ResponseEntity<Pokemon> crear(@RequestBody Pokemon pokemon) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pokemonService.save(pokemon));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un Pokémon por su ID", description = "Elimina un Pokémon del catálogo de la base de datos.")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (pokemonService.findById(id).isPresent()) {
            pokemonService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
