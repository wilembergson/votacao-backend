package votacao.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import votacao.backend.model.entity.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    UserDetails findByLogin(String login);

    Optional<Usuario> findByRole(String role);

    Optional<Usuario> findByCpf(Long cpf);
}
