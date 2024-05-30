package com.game.pixelchallengueback.service.imp;

import com.game.pixelchallengueback.Auth.JwtHelper;
import com.game.pixelchallengueback.dto.req.ScoreRequest;
import com.game.pixelchallengueback.dto.resp.ScoreDatosDTO;
import com.game.pixelchallengueback.dto.resp.ScoreResponse;
import com.game.pixelchallengueback.exp.ScoreNotRegister;
import com.game.pixelchallengueback.exp.UserNotRegister;
import com.game.pixelchallengueback.models.Game;
import com.game.pixelchallengueback.models.Score;
import com.game.pixelchallengueback.models.ScoreGameUser;
import com.game.pixelchallengueback.models.Usuario;
import com.game.pixelchallengueback.repository.GameRepository;
import com.game.pixelchallengueback.repository.ScoreRepository;
import com.game.pixelchallengueback.repository.UsuarioRepository;
import com.game.pixelchallengueback.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
    public class ScoreImp implements ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private GameRepository gameRepository;


    public Optional<Score> findByUserScore(ScoreGameUser clave) {
        return scoreRepository.findByGameUser(clave);
    }

    public ScoreResponse createScore(ScoreRequest scoreRequest) {
        JwtHelper jwtHelper = new JwtHelper();
        String usuario = jwtHelper.getUsernameFromToken(scoreRequest.getToken());


        Usuario resultadoUsuario = usuarioRepository.findByEmail(usuario).orElse(null);
        if (resultadoUsuario != null) {

            ScoreGameUser scoreGameUser = new ScoreGameUser();
            scoreGameUser.setUsuario(resultadoUsuario);
            Optional<Game> game = gameRepository.findById(0L);
            scoreGameUser.setGame(game.get());
            Score scoreAntiguo= scoreRepository.findByGameUser(scoreGameUser).orElse(null);
            System.out.println("scoreAntiguo:"+scoreAntiguo);
            if (scoreAntiguo != null && scoreAntiguo.getScore() >= 0 ) {
                System.out.println("Entra");
                Long scorenuevo = Long.parseLong(scoreRequest.getScore());
                System.out.println("Score nuevo:"+scorenuevo);
                boolean noSepuedeRegistrar = scoreAntiguo.getScore() >= scorenuevo;
                System.out.println("esmayor:"+noSepuedeRegistrar);
                if (noSepuedeRegistrar) {
                    throw new ScoreNotRegister("No se puede registrar el score");
                }
            }

            Score nuevoScore = new Score();
            nuevoScore.setGameUser(scoreGameUser);
            nuevoScore.setDate(new Date(System.currentTimeMillis()));
            if (scoreRequest.getScore() == null) {
                nuevoScore.setScore(0L);
            } else {
                nuevoScore.setScore(Long.parseLong(scoreRequest.getScore()));

            }
           scoreRepository.save(nuevoScore);
            return new ScoreResponse("Score guardado");

        } else {
            // Usuario no registrado, throw an exception
            throw new UserNotRegister("Usuario no registrado");

        }
    }



    //Obtiene las 3 personas con el mayor ranking
    @Override
    public List<ScoreDatosDTO> getAllScore() {
       List<ScoreDatosDTO> lista=scoreRepository.findAllByOrderByScoreDesc().stream().map(empresa -> ScoreDatosDTO.builder()
                        .score(empresa.getScore().toString())
                       .id(String.valueOf(empresa.getGameUser().getUsuario().getId()))
                        .name(empresa.getGameUser().getUsuario().getName())
                        .build()).limit(3)
                .collect(Collectors.toList());

        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).setRanking(String.valueOf(i + 1));
        }
        return lista;

    }

    public ScoreDatosDTO devuelveRanking(ScoreRequest tokenRequest) {
        System.out.println(tokenRequest.getToken());
        List<ScoreDatosDTO> lista=   scoreRepository.findAllByOrderByScoreDesc().stream().map(empresa ->
                        ScoreDatosDTO.builder()
                .score(empresa.getScore().toString())
                .name(empresa.getGameUser().getUsuario().getName())
                .id(String.valueOf(empresa.getGameUser().getUsuario().getId()))
                .build())
                .toList();


        JwtHelper jwtHelper = new JwtHelper();
        String usuario = jwtHelper.getUsernameFromToken(tokenRequest.getToken());
        Usuario usuarioToken = usuarioRepository.findByEmail(usuario).orElse(null);
        String elementoBuscado=String.valueOf(usuarioToken.getId());
        System.out.println(usuario);
        Game gameToken=gameRepository.findById(0L).orElse(null);
        ScoreGameUser scoreGameUser = new ScoreGameUser();
        scoreGameUser.setUsuario(usuarioToken);
        scoreGameUser.setGame(gameToken);
        Score busqueda=scoreRepository.findByGameUser(scoreGameUser).orElse(null);


       String elemento=null;
       int buscado=0;

        for (int i=0;i<lista.size() || elemento==elementoBuscado;i++) {
            elemento=String.valueOf(lista.get(i).getId());

            if(elemento.equals(elementoBuscado)) {break;}
        }
        String ranking=String.valueOf(buscado);
        System.out.println(ranking);

        return ScoreDatosDTO.builder()
                .ranking(ranking)
                .id(elementoBuscado)
                .name(usuarioToken.getName())
                .score(String.valueOf(busqueda.getScore()))
                .build();


    }
    @Override
    public List<ScoreDatosDTO> devuelveScore(ScoreRequest tokenRequest) {

        List<ScoreDatosDTO> lista= getAllScore();
        lista.add(devuelveRanking(tokenRequest));
        return lista;
    }

    public List<ScoreDatosDTO> devuelveScore(String tokenRequest) {

        List<ScoreDatosDTO> lista= getAllScore();
        lista.add(devuelveRanking(tokenRequest));
        return lista;
    }

    public ScoreDatosDTO devuelveRanking(String tokenRequest) {

        List<ScoreDatosDTO> lista=   scoreRepository.findAllByOrderByScoreDesc().stream().map(empresa ->
                        ScoreDatosDTO.builder()
                                .score(empresa.getScore().toString())
                                .name(empresa.getGameUser().getUsuario().getName())
                                .id(String.valueOf(empresa.getGameUser().getUsuario().getId()))
                                .build())
                .toList();


        JwtHelper jwtHelper = new JwtHelper();
        String usuario = jwtHelper.getUsernameFromToken(tokenRequest);
        Usuario usuarioToken = usuarioRepository.findByEmail(usuario).orElse(null);
        String elementoBuscado=String.valueOf(usuarioToken.getId());
        System.out.println(usuario);
        Game gameToken=gameRepository.findById(0L).orElse(null);
        ScoreGameUser scoreGameUser = new ScoreGameUser();
        scoreGameUser.setUsuario(usuarioToken);
        scoreGameUser.setGame(gameToken);
        Score busqueda=scoreRepository.findByGameUser(scoreGameUser).orElse(null);


        String elemento=null;
        int buscado=0;

        for (int i=0;i<lista.size() || elemento==elementoBuscado;i++) {
            elemento=String.valueOf(lista.get(i).getId());

            if(elemento.equals(elementoBuscado)) {break;}
        }
        String ranking=String.valueOf(buscado);
        System.out.println(ranking);

        return ScoreDatosDTO.builder()
                .ranking(ranking)
                .id(elementoBuscado)
                .name(usuarioToken.getName())
                .score(String.valueOf(busqueda.getScore()))
                .build();


    }

}





