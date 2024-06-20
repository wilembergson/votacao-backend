package votacao.backend.model.dto.Usuario;

public record NovoUsuarioDTO(
        String name,
        String cpf,
        String login,
        String password
) {
}
