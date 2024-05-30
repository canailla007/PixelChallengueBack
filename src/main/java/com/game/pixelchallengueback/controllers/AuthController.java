package com.game.pixelchallengueback.controllers;



import com.game.pixelchallengueback.Auth.JwtHelper;
import com.game.pixelchallengueback.config.AuthConfig;
import com.game.pixelchallengueback.dto.req.JwtRequest;
import com.game.pixelchallengueback.dto.req.UserRequestDto;
import com.game.pixelchallengueback.dto.resp.JwtResponse;
import com.game.pixelchallengueback.dto.resp.UserResponseDto;
import com.game.pixelchallengueback.exp.UserAlreadyExistsException;
import com.game.pixelchallengueback.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthConfig authConfig;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtHelper helper;


    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<JwtResponse> createUser(@RequestBody UserRequestDto userRequestDto) {
        try {
            UserResponseDto userResponseDto = userService.createUser(userRequestDto);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userResponseDto.getEmail());
            System.out.println("from db info");
            System.out.println(userDetails.getUsername());
            System.out.println(userDetails.getPassword());

            String token = this.helper.generateToken(userDetails);
            JwtResponse jwtResponse = JwtResponse.builder().token(token).build();
            return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException ex) {
            // Handle the exception and return an appropriate response
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new JwtResponse("User already exists: " + ex.getMessage()));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = this.helper.generateToken(userDetails);
        JwtResponse jwtResponse = JwtResponse.builder().token(token).build();
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        System.out.println("Login Info");
        System.out.println(email);
        System.out.println(password);
        System.out.println("------");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        System.out.println(authentication.toString()+"  do authenticarte");
        try {
            System.out.println("Started");
            manager.authenticate(authentication);
            System.out.println("Eneded");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Authentication successful for user: " + email);


        } catch (BadCredentialsException e) {
            System.out.println("Authentication not-successful for user: " + email);
            throw new BadCredentialsException(" Invalid Username or Password  !!");

        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler(BadCredentialsException ex) {
        return "Credentials Invalid !!";
    }
}
