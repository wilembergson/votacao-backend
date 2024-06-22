package votacao.backend.model.dto.Campanha;

public record CampanhaDTO(

        String titulo,

        String descricao,

        String inicio_votacao,

        String fim_votacao
) {
}
