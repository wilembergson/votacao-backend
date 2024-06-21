package votacao.backend.model.dto.Campanha;

import java.time.LocalDateTime;

public record CampanhaInfoDTO(

        String titulo,

        String descricao,

        Boolean votacao_aberta,

        LocalDateTime data_criacao,

        LocalDateTime inicio_votacao,

        LocalDateTime fim_votacao
) {
}
