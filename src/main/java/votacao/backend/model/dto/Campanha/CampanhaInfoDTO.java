package votacao.backend.model.dto.Campanha;

import votacao.backend.model.entity.Candidato;

import java.time.LocalDateTime;
import java.util.List;

public record CampanhaInfoDTO(

        String titulo,

        String descricao,

        Boolean votacao_aberta,

        LocalDateTime data_criacao,

        LocalDateTime inicio_votacao,

        LocalDateTime fim_votacao,

        List<Candidato> candidatos
) {
}
