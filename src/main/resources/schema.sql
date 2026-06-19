-- Crear el esquema si no existe
CREATE SCHEMA IF NOT EXISTS pokemon;

---
--- 1. Tabla: pokemon.pueblo
---
CREATE TABLE IF NOT EXISTS pokemon.pueblo (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    uuid UUID NOT NULL UNIQUE DEFAULT gen_random_uuid()
);

---
--- 2. Tabla: pokemon.tipo_pokemon
---
CREATE TABLE IF NOT EXISTS pokemon.tipo_pokemon (
    id SERIAL PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL,
    uuid UUID NOT NULL UNIQUE DEFAULT gen_random_uuid()
);

---
--- 3. Tabla: pokemon.entrenador
---
CREATE TABLE IF NOT EXISTS pokemon.entrenador (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    fecha_nacimiento DATE NOT NULL,
    fecha_vinculacion DATE NOT NULL,
    pueblo_id INT,
    uuid UUID NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    
    -- Llave foránea
    CONSTRAINT fk_entrenador_pueblo FOREIGN KEY (pueblo_id) 
        REFERENCES pokemon.pueblo(id) ON DELETE SET NULL
);

---
--- 4. Tabla: pokemon.pokemon
---
CREATE TABLE IF NOT EXISTS pokemon.pokemon (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    tipo_pokemon_id INT, -- Llave foránea mapeada de tipo_pokemon(fk)
    fecha_descubrimiento DATE NOT NULL,
    generacion INT NOT NULL,
    uuid UUID NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    
    -- Llave foránea
    CONSTRAINT fk_pokemon_tipo FOREIGN KEY (tipo_pokemon_id) 
        REFERENCES pokemon.tipo_pokemon(id) ON DELETE SET NULL
);

---
--- 5. Tabla: pokemon.pokemon_captura (Tabla intermedia / Relación muchos a muchos)
---
CREATE TABLE IF NOT EXISTS pokemon.pokemon_captura (
    pokemon_id INT,
    entrenador_id INT,
   
    -- Llave primaria compuesta
    PRIMARY KEY (pokemon_id, entrenador_id),
    
    -- Llaves foráneas
    CONSTRAINT fk_captura_pokemon FOREIGN KEY (pokemon_id) 
        REFERENCES pokemon.pokemon(id) ON DELETE CASCADE,
    CONSTRAINT fk_captura_entrenador FOREIGN KEY (entrenador_id) 
        REFERENCES pokemon.entrenador(id) ON DELETE CASCADE
);
