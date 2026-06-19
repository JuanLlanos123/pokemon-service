package com.pokemon.service;

import com.pokemon.entity.Pueblo;
import com.pokemon.repository.PuebloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PuebloServiceImpl implements PuebloService {

    private final PuebloRepository puebloRepository;

    public PuebloServiceImpl(PuebloRepository puebloRepository) {
        this.puebloRepository = puebloRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pueblo> findAll() {
        return puebloRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pueblo> findById(Integer id) {
        return puebloRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pueblo> findByUuid(UUID uuid) {
        return puebloRepository.findByUuid(uuid);
    }

    @Override
    public Pueblo save(Pueblo pueblo) {
        return puebloRepository.save(pueblo);
    }

    @Override
    public void deleteById(Integer id) {
        puebloRepository.deleteById(id);
    }
}
