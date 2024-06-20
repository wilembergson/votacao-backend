package votacao.backend.model.dto.Usuario;

public record UsuarioInformacoesDTO(
        String name,
        Long cpf,
        String login,
        String role
) {
}
