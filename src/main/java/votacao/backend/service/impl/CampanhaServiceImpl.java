package votacao.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import votacao.backend.exceptions.CustomException;
import votacao.backend.model.dto.Campanha.CampanhaInfoDTO;
import votacao.backend.model.dto.Campanha.NovaCampanhaDTO;
import votacao.backend.model.entity.Campanha;
import votacao.backend.repository.CampanhaRepository;
import votacao.backend.service.CampanhaService;
import votacao.backend.utils.DateConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CampanhaServiceImpl implements CampanhaService {

    @Autowired
    private CampanhaRepository campanhaReposritory;


    @Override
    public void novaCampanha(NovaCampanhaDTO dto) {
        Optional<Campanha> campanhaOpt = this.campanhaReposritory.findByTitulo(dto.titulo());
        if(campanhaOpt.isPresent())
            throw new CustomException("Já existe uma campanha com este título.", HttpStatus.CONFLICT);
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

    @Override
    public CampanhaInfoDTO obterPorId(String id) {
        Optional<Campanha> campanhaOpt = this.campanhaReposritory.findById(id);
        if(campanhaOpt.isEmpty())
            throw new CustomException("Campanha não encontrada.", HttpStatus.NOT_FOUND);
        Campanha cam = campanhaOpt.get();
        return new CampanhaInfoDTO(
                cam.getTitulo(),
                cam.getDescricao(),
                cam.getVotacao_aberta(),
                cam.getData_criacao(),
                cam.getInicio_votacao(),
                cam.getFim_votacao()
        );
    }

    @Override
    public List<Campanha> listar(Boolean votacao_aberta) {
        List<Campanha> lista = this.campanhaReposritory.findAll();
        return lista
                .stream()
                .filter(item -> item.getVotacao_aberta().equals(votacao_aberta))
                .collect(Collectors.toList());
    }
}
