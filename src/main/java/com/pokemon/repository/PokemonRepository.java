package com.pokemon.repository;

import com.pokemon.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
    Optional<Pokemon> findByUuid(UUID uuid);
}
