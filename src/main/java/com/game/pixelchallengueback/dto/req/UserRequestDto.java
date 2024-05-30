package com.game.pixelchallengueback.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

        private String name;
        private String email;
        private String password;
        private String nacionality;
        private String msisdn;


}
