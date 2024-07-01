package votacao.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campanha {

    @Id
    private String id;

    @Column
    private String titulo;

    @Column
    private String descricao;

    @Column
    private Boolean votacao_aberta;

    @Column
    private LocalDateTime data_criacao;

    @Column
    private LocalDateTime inicio_votacao;

    @Column
    private LocalDateTime fim_votacao;

    @OneToMany(mappedBy = "campanha", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Candidato> candidatos;

    @OneToMany(mappedBy = "campanha", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Voto> votos;
}
