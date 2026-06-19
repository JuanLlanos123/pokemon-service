package com.pokemon.exception;

/**
 * Se lanza cuando se intenta capturar un Pokémon que ya pertenece al entrenador.
 */
public class PokemonYaCapturadoException extends RuntimeException {
    public PokemonYaCapturadoException(String message) {
        super(message);
    }
}
