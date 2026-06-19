package com.pokemon.controller;

import com.pokemon.entity.Pokemon;
import com.pokemon.entity.TipoPokemon;
import com.pokemon.repository.TipoPokemonRepository;
import com.pokemon.service.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pokemons")
@Tag(name = "Pokémon", description = "Endpoints para la gestión del catálogo de Pokémon")
public class PokemonController {

    private final PokemonService pokemonService;
    private final TipoPokemonRepository tipoPokemonRepository;

    public PokemonController(PokemonService pokemonService, TipoPokemonRepository tipoPokemonRepository) {
        this.pokemonService = pokemonService;
        this.tipoPokemonRepository = tipoPokemonRepository;
    }

    // ─── DTOs ─────────────────────────────────────────────────────────────────

    /**
     * DTO de entrada para crear un nuevo Pokémon.
     * Usa snake_case para mantener consistencia con el schema SQL.
     */
    public record PokemonRequest(
            String nombre,
            String descripcion,
            LocalDate fecha_descubrimiento,
            Integer generacion,
            Integer tipo_pokemon_id
    ) {}

    // ─── Endpoints ────────────────────────────────────────────────────────────

    @GetMapping
    @Operation(summary = "Obtener todos los Pokémon", description = "Retorna una lista de todos los Pokémon registrados.")
    public ResponseEntity<List<Pokemon>> obtenerTodos() {
        return ResponseEntity.ok(pokemonService.findAll());
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(
            summary = "Listar Pokémon por tipo",
            description = "Filtra el catálogo de Pokémon por la descripción de su tipo (ej: 'Fuego'). " +
                          "Realiza un INNER JOIN entre pokemon y tipo_pokemon. La búsqueda no distingue mayúsculas.")
    public ResponseEntity<List<Pokemon>> obtenerPorTipo(@PathVariable String tipo) {
        List<Pokemon> resultado = pokemonService.findByTipo(tipo);
        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultado);
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
    @Operation(
            summary = "Crear un nuevo Pokémon",
            description = "Registra un nuevo Pokémon en el catálogo. " +
                          "El campo `tipo_pokemon_id` debe corresponder a un tipo existente. " +
                          "El UUID se genera automáticamente.")
    public ResponseEntity<?> crear(@RequestBody PokemonRequest request) {
        if (request.nombre() == null || request.fecha_descubrimiento() == null || request.generacion() == null) {
            return ResponseEntity.badRequest().body("Los campos 'nombre', 'fecha_descubrimiento' y 'generacion' son obligatorios.");
        }

        TipoPokemon tipoPokemon = null;
        if (request.tipo_pokemon_id() != null) {
            tipoPokemon = tipoPokemonRepository.findById(request.tipo_pokemon_id())
                    .orElse(null);
            if (tipoPokemon == null) {
                return ResponseEntity.badRequest().body("No existe un tipo_pokemon con id: " + request.tipo_pokemon_id());
            }
        }

        Pokemon pokemon = new Pokemon();
        pokemon.setNombre(request.nombre());
        pokemon.setDescripcion(request.descripcion());
        pokemon.setFechaDescubrimiento(request.fecha_descubrimiento());
        pokemon.setGeneracion(request.generacion());
        pokemon.setTipoPokemon(tipoPokemon);

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
