package com.game.pixelchallengueback.controllers;


import com.game.pixelchallengueback.dto.req.ScoreRequest;
import com.game.pixelchallengueback.dto.resp.ScoreResponse;
import com.game.pixelchallengueback.exp.UserNotRegister;
import com.game.pixelchallengueback.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    }
}
