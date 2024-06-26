package votacao.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import votacao.backend.exceptions.CustomException;
import votacao.backend.model.dto.Candidato.CandidatoDTO;
import votacao.backend.model.entity.Campanha;
import votacao.backend.model.entity.Candidato;
import votacao.backend.repository.CampanhaRepository;
import votacao.backend.repository.CandidatoRepository;
import votacao.backend.service.CandidatoService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CandidatoServiceImpl implements CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private CampanhaRepository campanhaRepository;

    @Override
    public void novoCandidato(String campanhaId, CandidatoDTO dto) {
        Integer numero = Integer.parseInt(dto.numero());
        Campanha campanha = buscarCampanha(campanhaId);
        List<Candidato> candidatos = campanha.getCandidatos()
                .stream()
                .filter(item -> item.getNumero().equals(numero) || item.getNome().equals(dto.nome()))
                .toList();
        if(!candidatos.isEmpty())
            throw new CustomException("Já existe um candidato com o nome ou o número informados.", HttpStatus.CONFLICT);
        Candidato candidato = new Candidato(
                UUID.randomUUID().toString(),
                dto.nome(),
                numero,
                campanha
        );
        candidatoRepository.save(candidato);
    }

    private Campanha buscarCampanha(String id){
        Optional<Campanha> campanhaOpt = campanhaRepository.findById(id);
        if(campanhaOpt.isEmpty())
            throw new CustomException("Campanha não cadastrada.", HttpStatus.NOT_FOUND);
        return campanhaOpt.get();
    }
}
