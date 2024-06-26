package votacao.backend.service;

import votacao.backend.model.dto.Candidato.BuscarCandidatoDTO;
import votacao.backend.model.dto.Candidato.CandidatoDTO;
import votacao.backend.model.entity.Candidato;

public interface CandidatoService {

    void novoCandidato(String campanhaId, CandidatoDTO dto);

    Candidato buscarPorNumero(BuscarCandidatoDTO dto);

    void deletarPorId(String id);
}
