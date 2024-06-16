package votacao.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import votacao.backend.model.entity.Usuario;
import votacao.backend.repository.UsuarioRepository;
import votacao.backend.utils.RolesEnum;

import java.util.UUID;

@Component
public class UserAdminLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByRole(RolesEnum.ADMIN.getRoleName()).isEmpty()){
            Usuario admin = new Usuario(
                    UUID.randomUUID().toString(),
                    "Administrador",
                    null,
                    "user.admin",
                    new BCryptPasswordEncoder().encode("administrador123"),
                    RolesEnum.ADMIN.getRoleName()
            );
            userRepository.save(admin);
        }
    }
}
