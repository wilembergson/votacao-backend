package votacao.backend.model.dto.Voto;

public record ComprovanteVotoDTO(
        String id_campanha,
        String campanha,
        String votante,
        String cpf,
        String id_voto,
        String id_candidato,
        String candidato
) {
}
