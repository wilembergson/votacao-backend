package votacao.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import votacao.backend.exceptions.CustomException;
import votacao.backend.model.dto.Usuario.LoginDTO;
import votacao.backend.model.dto.Usuario.NovoUsuarioDTO;
import votacao.backend.model.dto.Usuario.UsuarioInformacoesDTO;
import votacao.backend.model.entity.Usuario;
import votacao.backend.repository.UsuarioRepository;
import votacao.backend.service.UsuarioService;
import votacao.backend.utils.RolesEnum;

import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void novoUsuario(NovoUsuarioDTO dto) {
        Optional<Usuario> usuarioExistente = this.usuarioRepository.findByCpf(Long.parseLong(dto.cpf()));
        if(usuarioExistente.isPresent())
            throw new CustomException("Usuário já registrado com o CPF "+dto.cpf(), HttpStatus.CONFLICT);
        Usuario usuario = new Usuario(
                UUID.randomUUID().toString(),
                dto.name(),
                Long.parseLong(dto.cpf()),
                dto.login(),
                new BCryptPasswordEncoder().encode(dto.password()),
                RolesEnum.COMMON.getRoleName()
        );
        usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioInformacoesDTO obterInformacoes(String login) {
        //Optional<Usuario> usuarioOpt = this.usuarioRepository.findByCpf(cpf);
        Usuario usuarioOpt = (Usuario) usuarioRepository.findByLogin(login);
        if(usuarioOpt == null)
            throw new CustomException("Usuário não registrado.", HttpStatus.NOT_FOUND);
        return new UsuarioInformacoesDTO(
                usuarioOpt.getName(),
                usuarioOpt.getCpf(),
                usuarioOpt.getLogin(),
                usuarioOpt.getRole()
        );
    }

    @Override
    public void atualizarAcesso(String loginAtual, LoginDTO dto) {
        Usuario usuario = this.obterUsuarioPorCpf(loginAtual);
        String novaSenha = new BCryptPasswordEncoder().encode(dto.password());
        usuario.setLogin(dto.login());
        usuario.setPassword(novaSenha);
        usuarioRepository.save(usuario);
    }

    private Usuario obterUsuarioPorCpf(String login){
        Usuario usuario = (Usuario) this.usuarioRepository.findByLogin(login);
        if(usuario == null)
            throw new CustomException("Usuário não registrado.", HttpStatus.NOT_FOUND);
        return usuario;
    }
}
