package com.game.pixelchallengueback.dto.resp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String name;
    private String email;
    private String nacionality;
    private String msisdn;
    private long  id;
}
