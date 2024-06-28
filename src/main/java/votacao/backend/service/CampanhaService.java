package votacao.backend.service;

import votacao.backend.model.dto.Campanha.CampanhaDTO;
import votacao.backend.model.entity.Campanha;
import votacao.backend.model.entity.Candidato;

import java.util.List;

public interface CampanhaService {
    void novaCampanha(CampanhaDTO dto);

    Campanha buscarPorId(String id);

    List<Campanha> listar(Boolean votacao_aberta);

    void atualizar(String id, CampanhaDTO dto);

}
