package com.pokemon.service;

import com.pokemon.entity.Entrenador;
import com.pokemon.entity.Pokemon;
import com.pokemon.exception.PokemonYaCapturadoException;
import com.pokemon.repository.EntrenadorRepository;
import com.pokemon.repository.PokemonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EntrenadorServiceImpl implements EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;
    private final PokemonRepository pokemonRepository;

    public EntrenadorServiceImpl(EntrenadorRepository entrenadorRepository, PokemonRepository pokemonRepository) {
        this.entrenadorRepository = entrenadorRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entrenador> findAll() {
        return entrenadorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Entrenador> findById(Integer id) {
        return entrenadorRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Entrenador> findByUuid(UUID uuid) {
        return entrenadorRepository.findByUuid(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Entrenador> findByEmail(String email) {
        return entrenadorRepository.findByEmail(email);
    }

    @Override
    public Entrenador save(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    @Override
    public void deleteById(Integer id) {
        entrenadorRepository.deleteById(id);
    }

    @Override
    public void capturarPokemon(Integer entrenadorId, Integer pokemonId) {
        Entrenador entrenador = entrenadorRepository.findById(entrenadorId)
                .orElseThrow(() -> new IllegalArgumentException("Entrenador no encontrado con ID: " + entrenadorId));
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new IllegalArgumentException("Pokémon no encontrado con ID: " + pokemonId));

        pokemon.getEntrenadores().add(entrenador);
        pokemonRepository.save(pokemon);
    }

    @Override
    public void liberarPokemon(Integer entrenadorId, Integer pokemonId) {
        Entrenador entrenador = entrenadorRepository.findById(entrenadorId)
                .orElseThrow(() -> new IllegalArgumentException("Entrenador no encontrado con ID: " + entrenadorId));
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new IllegalArgumentException("Pokémon no encontrado con ID: " + pokemonId));

        pokemon.getEntrenadores().remove(entrenador);
        pokemonRepository.save(pokemon);
    }

    @Override
    public List<Pokemon> capturarPokemonPorUuid(UUID uuidEntrenador, UUID uuidPokemon) {
        Entrenador entrenador = entrenadorRepository.findByUuid(uuidEntrenador)
                .orElseThrow(() -> new IllegalArgumentException("Entrenador no encontrado con UUID: " + uuidEntrenador));
        Pokemon pokemon = pokemonRepository.findByUuid(uuidPokemon)
                .orElseThrow(() -> new IllegalArgumentException("Pokémon no encontrado con UUID: " + uuidPokemon));

        // Verificar duplicado antes de insertar
        if (pokemonRepository.existsByEntrenadorUuidAndPokemonUuid(uuidEntrenador, uuidPokemon)) {
            throw new PokemonYaCapturadoException("pokemos ya esta registardo al entrandor");
        }

        pokemon.getEntrenadores().add(entrenador);
        pokemonRepository.save(pokemon);

        // Retornar la lista actualizada de pokémones del entrenador
        return pokemonRepository.findByEntrenadorUuid(uuidEntrenador);
    }
}
