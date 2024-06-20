package votacao.backend.service;

import votacao.backend.model.dto.Campanha.NovaCampanhaDTO;

public interface CampanhaService {
    void novaCampanha(NovaCampanhaDTO dto);
}
