package votacao.backend.service;

import votacao.backend.model.dto.Campanha.CampanhaInfoDTO;
import votacao.backend.model.dto.Campanha.CampanhaDTO;
import votacao.backend.model.entity.Campanha;

import java.util.List;

public interface CampanhaService {
    void novaCampanha(CampanhaDTO dto);

    CampanhaInfoDTO obterPorId(String id);

    List<Campanha> listar(Boolean votacao_aberta);

    void atualizar(String id, CampanhaDTO dto);
}
