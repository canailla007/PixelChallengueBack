package com.game.pixelchallengueback.repository;

import com.game.pixelchallengueback.models.Score;
import com.game.pixelchallengueback.models.ScoreGameUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScoreRepository
        extends JpaRepository<Score, ScoreGameUser> {
    Optional<Score> findByGameUser(ScoreGameUser clave);
}