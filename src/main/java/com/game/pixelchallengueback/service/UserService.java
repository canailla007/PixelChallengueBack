package com.game.pixelchallengueback.service;


import com.game.pixelchallengueback.dto.req.UserRequestDto;
import com.game.pixelchallengueback.dto.resp.UserResponseDto;
import com.game.pixelchallengueback.models.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserResponseDto> getAllUser();
    UserResponseDto createUser(UserRequestDto userRequestDto);
    Usuario buscarUsuario(String email);
}
