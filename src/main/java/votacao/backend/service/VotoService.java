package votacao.backend.service;

import votacao.backend.model.dto.Voto.VotoDTO;

public interface VotoService {
    void novoVoto(VotoDTO dto, String login, String password);
}
