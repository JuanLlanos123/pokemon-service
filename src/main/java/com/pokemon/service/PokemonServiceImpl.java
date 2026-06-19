package com.pokemon.service;

import com.pokemon.entity.Pokemon;
import com.pokemon.repository.PokemonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepository;

    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pokemon> findAll() {
        return pokemonRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pokemon> findByTipo(String tipo) {
        return pokemonRepository.findByTipoPokemon_DescripcionIgnoreCase(tipo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pokemon> findByEntrenadorUuid(UUID uuid) {
        return pokemonRepository.findByEntrenadorUuid(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pokemon> findById(Integer id) {
        return pokemonRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pokemon> findByUuid(UUID uuid) {
        return pokemonRepository.findByUuid(uuid);
    }

    @Override
    public Pokemon save(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    @Override
    public void deleteById(Integer id) {
        pokemonRepository.deleteById(id);
    }
}
