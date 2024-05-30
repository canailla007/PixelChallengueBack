package com.game.pixelchallengueback.service;


import com.game.pixelchallengueback.models.Nacionalidad;
import com.game.pixelchallengueback.repository.NacionalidadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class NacionalidadService {
    private final NacionalidadRepository nacionalidadRepository;

    public Optional<Nacionalidad> findByNombre(String Name) {
        return nacionalidadRepository.findByNombre(Name);
    }

    public Optional<Nacionalidad> findById(Long Id) {
        return nacionalidadRepository.findById(Id);
    }

}
