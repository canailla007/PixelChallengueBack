package com.game.pixelchallengueback.service.imp;

import com.game.pixelchallengueback.config.AuthConfig;
import com.game.pixelchallengueback.dto.req.UserRequestDto;
import com.game.pixelchallengueback.dto.resp.UserResponseDto;
import com.game.pixelchallengueback.exp.UserAlreadyExistsException;
import com.game.pixelchallengueback.models.Usuario;
import com.game.pixelchallengueback.models.Nacionalidad;
import com.game.pixelchallengueback.repository.NacionalidadRepository;
import com.game.pixelchallengueback.repository.UsuarioRepository;

import com.game.pixelchallengueback.service.UserService;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserImp implements UserService {

    @Autowired
    private UsuarioRepository userRepo;
    @Autowired
    private NacionalidadRepository nacionalidadRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthConfig authConfig;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepo.findByEmail(username).orElseThrow(()->new RuntimeException("User not found"));

    }

    @Override
    public List<UserResponseDto> getAllUser() {
        List<Usuario> userEnitiys = userRepo.findAll();
        return userEnitiys.stream().map(this::userEntityToUserRespDto).collect(Collectors.toList());



    }
    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        Optional<Usuario> foundUser = this.userRepo.findByEmail(userRequestDto.getEmail());
        if (foundUser.isEmpty()) {
            Usuario user = this.userReqDtoToUserEntity(userRequestDto);
            user.setPassword(authConfig.passwordEncoder().encode(user.getPassword()));
            user.setNacionalidad(buscarNacionalidad(userRequestDto.getNacionality()));
            Usuario createdUser = userRepo.save(user);
            return this.userEntityToUserRespDto(createdUser);
        } else {
            // User already exists, throw an exception
            throw new UserAlreadyExistsException("User with email " + userRequestDto.getEmail() + " already exists");
        }
    }

    public Nacionalidad buscarNacionalidad(String Nombre) {
        Nacionalidad resultado=nacionalidadRepository.findByNombre(Nombre).orElse(null);
        if (resultado!=null) {
            return resultado;
        }
        else {
            Nacionalidad nuevaNacionalidad=new Nacionalidad();
            nuevaNacionalidad.setNombre(Nombre);
            return nacionalidadRepository.save(nuevaNacionalidad);}

    }


    public Usuario userReqDtoToUserEntity(UserRequestDto userReqDto){
        return this.modelMapper.map(userReqDto,Usuario.class);

    }
    public UserResponseDto userEntityToUserRespDto(Usuario user){
         Optional<Nacionalidad> resultado=nacionalidadRepository.findById(user.getNacionalidad().getId());
       UserResponseDto mapeoUser= this.modelMapper.map(user,UserResponseDto.class);
       mapeoUser.setNacionality(resultado.get().getNombre());
       return mapeoUser;

    }
    public Usuario buscarUsuario(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("Usuario no encontrado"));

    }
}
