package votacao.backend.model.dto;

public record UsuarioInformacoesDTO(
        String name,
        Long cpf,
        String login,
        String role
) {
}
