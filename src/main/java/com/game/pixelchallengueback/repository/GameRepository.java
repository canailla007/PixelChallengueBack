package com.game.pixelchallengueback.repository;

import com.game.pixelchallengueback.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game,Long> {

    Optional<Game> findById(Long id);

}
