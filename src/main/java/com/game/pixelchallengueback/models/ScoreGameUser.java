package com.game.pixelchallengueback.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ScoreGameUser {
    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Usuario usuario;

}
