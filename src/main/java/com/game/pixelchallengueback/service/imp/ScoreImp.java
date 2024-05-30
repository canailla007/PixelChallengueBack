package com.game.pixelchallengueback.service.imp;

import com.game.pixelchallengueback.Auth.JwtHelper;
import com.game.pixelchallengueback.dto.req.ScoreRequest;
import com.game.pixelchallengueback.dto.resp.ScoreResponse;
import com.game.pixelchallengueback.exp.UserNotRegister;
import com.game.pixelchallengueback.models.Game;
import com.game.pixelchallengueback.models.Score;
import com.game.pixelchallengueback.models.ScoreGameUser;
import com.game.pixelchallengueback.models.Usuario;
import com.game.pixelchallengueback.repository.GameRepository;
import com.game.pixelchallengueback.repository.ScoreRepository;
import com.game.pixelchallengueback.repository.UsuarioRepository;
import com.game.pixelchallengueback.service.ScoreService;
import com.game.pixelchallengueback.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

 @Service
    public class ScoreImp implements ScoreService {
     @Autowired
     private ScoreRepository scoreRepository;
     @Autowired
     private  UsuarioRepository usuarioRepository;
     @Autowired
     private GameRepository gameRepository;
     @Autowired
     private ModelMapper modelMapper;

     public Optional<Score> findByUserScore(ScoreGameUser clave) {
         return scoreRepository.findByGameUser(clave);
     }

     public ScoreResponse createScore(ScoreRequest scoreRequest) {
         JwtHelper jwtHelper = new JwtHelper();
         String usuario = jwtHelper.getUsernameFromToken(scoreRequest.getToken());
         String date = jwtHelper.getExpirationDateFromToken(scoreRequest.getToken()).toString();

         Usuario resultadoUsuario = usuarioRepository.findByEmail(usuario).orElse(null);
         if (resultadoUsuario != null) {
             ScoreGameUser scoreGameUser = new ScoreGameUser();
             scoreGameUser.setUsuario(resultadoUsuario);
             Optional<Game> game = gameRepository.findById(1L);
             scoreGameUser.setGame(game.get());
             Score nuevoScore = new Score();
             nuevoScore.setGameUser(scoreGameUser);
             if (scoreRequest.getScore() == null) {
                 nuevoScore.setScore(0L);
             } else {
                 nuevoScore.setScore(Long.parseLong(scoreRequest.getScore()));

             }
             var score = scoreRepository.save(nuevoScore);
             return new ScoreResponse("Score guardado");

         } else {
             // Usuario no registrado, throw an exception
             throw new UserNotRegister("Usuario no registrado");

         }
     }


 }

