package com.game.pixelchallengueback.dto.req;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ScoreRequest {
    private String score;
    private String token;
    private String juego_id;
}
