package votacao.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import votacao.backend.utils.RolesEnum;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private Long cpf;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String role;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Voto> votos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(getRole().equals(RolesEnum.ADMIN.getRoleName())){
            return List.of(new SimpleGrantedAuthority("ROLE_"+RolesEnum.ADMIN.getRoleName()));
        }else{
            return List.of(new SimpleGrantedAuthority("ROLE_"+RolesEnum.COMMON.getRoleName()));
        }
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
