package com.pokemon.service;

import com.pokemon.entity.TipoPokemon;
import com.pokemon.repository.TipoPokemonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TipoPokemonServiceImpl implements TipoPokemonService {

    private final TipoPokemonRepository tipoPokemonRepository;

    public TipoPokemonServiceImpl(TipoPokemonRepository tipoPokemonRepository) {
        this.tipoPokemonRepository = tipoPokemonRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoPokemon> findAll() {
        return tipoPokemonRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoPokemon> findById(Integer id) {
        return tipoPokemonRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoPokemon> findByUuid(UUID uuid) {
        return tipoPokemonRepository.findByUuid(uuid);
    }

    @Override
    public TipoPokemon save(TipoPokemon tipoPokemon) {
        return tipoPokemonRepository.save(tipoPokemon);
    }

    @Override
    public void deleteById(Integer id) {
        tipoPokemonRepository.deleteById(id);
    }
}
