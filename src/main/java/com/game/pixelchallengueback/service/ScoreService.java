package com.game.pixelchallengueback.service;



import com.game.pixelchallengueback.dto.req.ScoreRequest;
import com.game.pixelchallengueback.dto.resp.ScoreDatosDTO;
import com.game.pixelchallengueback.dto.resp.ScoreResponse;
import com.game.pixelchallengueback.models.*;


import java.util.List;
import java.util.Optional;

public interface ScoreService {
    Optional<Score> findByUserScore(ScoreGameUser scoreGameUser);
    ScoreResponse createScore(ScoreRequest scoreRequest);
    List<ScoreDatosDTO> devuelveScore(ScoreRequest tokenRequest);
    List<ScoreDatosDTO> devuelveScore(String tokenRequest);
    List<ScoreDatosDTO> getAllScore();

}




