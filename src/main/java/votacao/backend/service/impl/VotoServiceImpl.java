package votacao.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import votacao.backend.exceptions.CustomException;
import votacao.backend.model.dto.Voto.VotoDTO;
import votacao.backend.model.entity.Campanha;
import votacao.backend.model.entity.Candidato;
import votacao.backend.model.entity.Usuario;
import votacao.backend.model.entity.Voto;
import votacao.backend.repository.CampanhaRepository;
import votacao.backend.repository.CandidatoRepository;
import votacao.backend.repository.UsuarioRepository;
import votacao.backend.repository.VotoRepository;
import votacao.backend.service.VotoService;

import java.util.Optional;
import java.util.UUID;

@Service
public class VotoServiceImpl implements VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private CampanhaRepository campanhaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Override
    public void novoVoto(VotoDTO dto, Long cpf) {
        Optional<Campanha> campanhaOpt = campanhaRepository.findById(dto.id_campanha());
        if(campanhaOpt.isEmpty())
            throw new CustomException("ID da campanha invátido.", HttpStatus.NOT_FOUND);

        Optional<Usuario> usuarioOpt = usuarioRepository.findByCpf(cpf);
        if(usuarioOpt.isEmpty())
            throw new CustomException("CPF não cadastrado.", HttpStatus.NOT_FOUND);

        Optional<Candidato> candidatoOpt = campanhaOpt.get()
                .getCandidatos()
                .stream()
                .filter(item -> item.getId().equals(dto.id_candidato()))
                .findFirst();
        if(candidatoOpt.isEmpty())
            throw new CustomException("Candidato não existente nesta campanha.", HttpStatus.NOT_FOUND);

        Optional<Voto> votoOpt = campanhaOpt.get()
                .getVotos()
                .stream()
                .filter(item -> item.getUsuario().getCpf().equals(cpf) && item.getCandidato().getId().equals(dto.id_candidato()))
                .findFirst();
        if(votoOpt.isPresent())
            throw new CustomException("Seu voto já foi registrado.", HttpStatus.CONFLICT);

        Voto voto = new Voto(
                UUID.randomUUID().toString(),
                campanhaOpt.get(),
                usuarioOpt.get(),
                candidatoOpt.get()
        );
        votoRepository.save(voto);
    }
}
