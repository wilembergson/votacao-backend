package votacao.backend.service;

import votacao.backend.model.dto.Voto.ComprovanteVotoDTO;
import votacao.backend.model.dto.Voto.VotoDTO;

public interface VotoService {
    ComprovanteVotoDTO novoVoto(VotoDTO dto, String login, String password);
}
