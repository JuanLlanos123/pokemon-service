package com.pokemon.repository;

import com.pokemon.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
    Optional<Pokemon> findByUuid(UUID uuid);
    List<Pokemon> findByTipoPokemon_DescripcionIgnoreCase(String descripcion);

    /**
     * Triple JOIN: pokemon.entrenador → pokemon.pokemon_captura → pokemon.pokemon
     * Filtra por el UUID del entrenador.
     */
    @Query("SELECT p FROM Pokemon p JOIN p.entrenadores e WHERE e.uuid = :uuid")
    List<Pokemon> findByEntrenadorUuid(@Param("uuid") UUID uuid);

    /**
     * Verifica si ya existe la relación entre el entrenador y el pokémon
     * en la tabla intermedia pokemon_captura.
     */
    @Query("SELECT COUNT(p) > 0 FROM Pokemon p JOIN p.entrenadores e WHERE e.uuid = :entrenadorUuid AND p.uuid = :pokemonUuid")
    boolean existsByEntrenadorUuidAndPokemonUuid(
            @Param("entrenadorUuid") UUID entrenadorUuid,
            @Param("pokemonUuid")   UUID pokemonUuid
    );
}
