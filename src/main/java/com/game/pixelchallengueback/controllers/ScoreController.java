package com.game.pixelchallengueback.controllers;



import com.game.pixelchallengueback.dto.req.ScoreRequest;
import com.game.pixelchallengueback.dto.resp.ScoreDatosDTO;
import com.game.pixelchallengueback.dto.resp.ScoreResponse;
import com.game.pixelchallengueback.exp.ScoreNotRegister;
import com.game.pixelchallengueback.exp.UserNotRegister;
import com.game.pixelchallengueback.service.ScoreService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController {

 @Autowired
    private ScoreService scoreService;

    @PostMapping("/create")
    public ResponseEntity<ScoreResponse> createScore(@RequestBody ScoreRequest scoreRequestDto) {
        try {
            ScoreResponse respuesta=scoreService.createScore(scoreRequestDto);

            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (UserNotRegister ex) {
            // Handle the exception and return an appropriate response
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ScoreResponse("Token no valido"));
        }
     catch (ScoreNotRegister ey) {
        // Handle the exception and return an appropriate response
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ScoreResponse("Score menor al guardado"));
         }
    }
    @GetMapping("/all")
    public ResponseEntity<List<ScoreDatosDTO>> getAllUser(){
        return new ResponseEntity<>( scoreService.getAllScore(), HttpStatus.OK);
    }

    @PostMapping("/ranking")
    public ResponseEntity<List<ScoreDatosDTO>> devuelveRanking(@RequestBody ScoreRequest tokenRequest) {
        return new ResponseEntity<>( scoreService.devuelveScore(tokenRequest), HttpStatus.OK);
    }

    @PostMapping("/store")
    public ResponseEntity<List<ScoreDatosDTO>> guardarNuevoTransDeliveryPlanning(
            @RequestHeader("Authorization") String bearerToken) {
        // Aquí puedes usar el valor de 'bearerToken' como necesites
        System.out.println("Bearer Token recibido: " + bearerToken);
        try {
            return new ResponseEntity<>(scoreService.devuelveScore(bearerToken.substring(7)), HttpStatus.OK);
        } catch (ExpiredJwtException ex) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/home")
    public String get(){
        return "Prueba inicio";
    }
}

