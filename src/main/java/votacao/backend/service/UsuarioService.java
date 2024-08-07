package votacao.backend.service;

import votacao.backend.model.dto.Usuario.LoginDTO;
import votacao.backend.model.dto.Usuario.NovoUsuarioDTO;
import votacao.backend.model.dto.Usuario.UsuarioInformacoesDTO;

public interface UsuarioService {
    void novoUsuario(NovoUsuarioDTO dto);

    UsuarioInformacoesDTO obterInformacoes(String login);

    void atualizarAcesso(String loginAtual, LoginDTO dto);
}
