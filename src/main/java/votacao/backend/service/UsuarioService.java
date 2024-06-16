package votacao.backend.service;

import votacao.backend.model.dto.NovoUsuarioDTO;

public interface UsuarioService {
    void novoUsuario(NovoUsuarioDTO dto);
}
