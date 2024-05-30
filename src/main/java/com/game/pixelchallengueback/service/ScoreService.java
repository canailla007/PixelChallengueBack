package com.game.pixelchallengueback.service;


import com.game.pixelchallengueback.Auth.JwtHelper;
import com.game.pixelchallengueback.dto.req.ScoreRequest;
import com.game.pixelchallengueback.dto.req.UserRequestDto;
import com.game.pixelchallengueback.dto.resp.ScoreResponse;
import com.game.pixelchallengueback.dto.resp.UserResponseDto;
import com.game.pixelchallengueback.exp.UserNotRegister;
import com.game.pixelchallengueback.models.*;
import com.game.pixelchallengueback.repository.GameRepository;
import com.game.pixelchallengueback.repository.ScoreRepository;
import com.game.pixelchallengueback.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ScoreService {
    Optional<Score> findByUserScore(ScoreGameUser scoreGameUser);
    ScoreResponse createScore(ScoreRequest scoreRequest);

}




