package votacao.backend.service;

import votacao.backend.model.dto.Candidato.CandidatoDTO;

public interface CandidatoService {

    void novoCandidato(String campanhaId, CandidatoDTO dto);
}
