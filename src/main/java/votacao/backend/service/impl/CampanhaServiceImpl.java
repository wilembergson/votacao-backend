package votacao.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import votacao.backend.model.dto.Campanha.NovaCampanhaDTO;
import votacao.backend.model.entity.Campanha;
import votacao.backend.repository.CampanhaReposritory;
import votacao.backend.service.CampanhaService;
import votacao.backend.utils.DateConverter;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CampanhaServiceImpl implements CampanhaService {

    @Autowired
    private CampanhaReposritory campanhaReposritory;


    @Override
    public void novaCampanha(NovaCampanhaDTO dto) {
        Campanha campanha = new Campanha();
        campanha.setId(UUID.randomUUID().toString());
        campanha.setTitulo(dto.titulo());
        campanha.setDescricao(dto.descricao());
        campanha.setVotacao_aberta(false);
        campanha.setData_criacao(LocalDateTime.now());
        campanha.setInicio_votacao(DateConverter.stringToLocalDateTime(dto.inicio_votacao()));
        campanha.setFim_votacao(DateConverter.stringToLocalDateTime(dto.fim_votacao()));
        campanhaReposritory.save(campanha);
    }
}
