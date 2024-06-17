package votacao.backend.service;

import votacao.backend.model.dto.LoginDTO;
import votacao.backend.model.dto.NovoUsuarioDTO;
import votacao.backend.model.dto.UsuarioInformacoesDTO;

public interface UsuarioService {
    void novoUsuario(NovoUsuarioDTO dto);

    UsuarioInformacoesDTO obterInformacoes(Long cpf);

    void atualizarAcesso(Long cpf, LoginDTO dto);
}
