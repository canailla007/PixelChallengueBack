package com.game.pixelchallengueback.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Score {
    @EmbeddedId
    ScoreGameUser gameUser;
    private Long score;
    private Date date;
}
