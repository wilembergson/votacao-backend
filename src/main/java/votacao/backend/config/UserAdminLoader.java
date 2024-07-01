package votacao.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import votacao.backend.model.entity.Usuario;
import votacao.backend.repository.UsuarioRepository;
import votacao.backend.utils.RolesEnum;

import java.util.UUID;

@Component
public class UserAdminLoader implements CommandLineRunner {

    @Value("${api.security.user.admin}")
    private String adm;

    @Value("${api.security.user.password}")
    private String password;

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByRole(RolesEnum.ADMIN.getRoleName()).isEmpty()){
            Usuario admin = new Usuario(
                    UUID.randomUUID().toString(),
                    "Administrador",
                    null,
                    adm,
                    new BCryptPasswordEncoder().encode(password),
                    RolesEnum.ADMIN.getRoleName(),
                    null
            );
            userRepository.save(admin);
        }
    }
}
