package votacao.backend.model.dto;

public record NovoUsuarioDTO(
        String name,
        String cpf,
        String login,
        String password
) {
}
