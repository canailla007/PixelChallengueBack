package com.game.pixelchallengueback;

import com.game.pixelchallengueback.Auth.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class JwtHelperTest {
    @Test
    public void prueba1(){
        String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwZXBlNEB0ZXJyYS5lcyIsImlhdCI6MTcxNzAyMTI4NywiZXhwIjoxNzE3MDM5Mjg3fQ.7BW-VREtOrwAuvKAGN3Q3ZrqJjK_ec93f4BpNR8da-OjTZ7Q0OVaqF3OYnaTEObCzSAtR64KDOxFNXxX8NLbTA";
        JwtHelper jwtHelper = new JwtHelper();
        String usuario=jwtHelper.getUsernameFromToken(token);
        String date=jwtHelper.getExpirationDateFromToken(token).toString();

        assertEquals("El usuario debería de ser pepe4@terra.es","pepe4@terra.es",usuario);
        assertEquals("La fecha debería de ser :Thu May 30 05:21:27 CEST 2024","Thu May 30 05:21:27 CEST 2024",date);


    }
}
