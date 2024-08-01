package votacao.backend.service;

import votacao.backend.model.dto.Campanha.CampanhaDTO;
import votacao.backend.model.dto.Campanha.CampanhaInfoDTO;
import votacao.backend.model.entity.Campanha;

import java.util.List;

public interface CampanhaService {
    void novaCampanha(CampanhaDTO dto);

    Campanha buscarPorId(String id);

    List<CampanhaInfoDTO> listar();

    void atualizar(String id, CampanhaDTO dto);

}
