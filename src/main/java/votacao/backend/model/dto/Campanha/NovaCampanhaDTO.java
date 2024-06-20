package votacao.backend.model.dto.Campanha;

import java.time.LocalDateTime;

public record NovaCampanhaDTO(

        String titulo,

        String descricao,

        String inicio_votacao,

        String fim_votacao
) {
}
