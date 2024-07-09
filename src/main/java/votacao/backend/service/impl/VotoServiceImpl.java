package votacao.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import votacao.backend.exceptions.CustomException;
import votacao.backend.model.dto.Usuario.LoginResponseDTO;
import votacao.backend.model.dto.Voto.VotoDTO;
import votacao.backend.model.entity.Campanha;
import votacao.backend.model.entity.Candidato;
import votacao.backend.model.entity.Usuario;
import votacao.backend.model.entity.Voto;
import votacao.backend.repository.CampanhaRepository;
import votacao.backend.repository.CandidatoRepository;
import votacao.backend.repository.UsuarioRepository;
import votacao.backend.repository.VotoRepository;
import votacao.backend.service.VotoService;

import java.util.Optional;
import java.util.UUID;

@Service
public class VotoServiceImpl implements VotoService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private CampanhaRepository campanhaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Override
    public void novoVoto(VotoDTO dto, String login, String password) {
        Optional<Campanha> campanhaOpt = campanhaRepository.findById(dto.id_campanha());
        if(campanhaOpt.isEmpty())
            throw new CustomException("ID da campanha invátido.", HttpStatus.NOT_FOUND);

        Optional<Candidato> candidatoOpt = campanhaOpt.get()
                .getCandidatos()
                .stream()
                .filter(item -> item.getId().equals(dto.id_candidato()))
                .findFirst();
        if(candidatoOpt.isEmpty())
            throw new CustomException("Candidato não existente nesta campanha.", HttpStatus.NOT_FOUND);

        campanhaOpt.get()
                .getVotos()
                .stream()
                .forEach(item -> checarVoto(login, password, item.getUsuario_hash()));

        Usuario usuario = obterUsuario(login, password);
        Voto voto = new Voto(
                UUID.randomUUID().toString(),
                bcrypt.encode(usuario.getCpf().toString()),
                campanhaOpt.get(),
                candidatoOpt.get()
        );
        votoRepository.save(voto);
    }

    private void checarVoto(String login, String password, String usuario_hash){
        Usuario usuario = obterUsuario(login, password);
        if(bcrypt.matches(usuario.getCpf().toString(), usuario_hash) == true){
            throw new CustomException("Você já votou.", HttpStatus.FORBIDDEN);
        }
    }

    private Usuario obterUsuario(String login, String password){
        try{
            var usernamePassword = new UsernamePasswordAuthenticationToken(login, password);
            var auth = this.authenticationManager.authenticate(usernamePassword);
            return (Usuario) auth.getPrincipal();
        } catch (Exception e){
            throw new CustomException("Senha inválida.", HttpStatus.UNAUTHORIZED);
        }
    }
}
