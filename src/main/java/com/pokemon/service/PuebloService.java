package com.pokemon.service;

import com.pokemon.entity.Pueblo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PuebloService {
    List<Pueblo> findAll();
    Optional<Pueblo> findById(Integer id);
    Optional<Pueblo> findByUuid(UUID uuid);
    Pueblo save(Pueblo pueblo);
    void deleteById(Integer id);
}
