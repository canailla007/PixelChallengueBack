package com.game.pixelchallengueback.repository;

import com.game.pixelchallengueback.models.Nacionalidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NacionalidadRepository
        extends JpaRepository<Nacionalidad,	Long> {
    Optional<Nacionalidad> findByNombre(String nombre);

}
