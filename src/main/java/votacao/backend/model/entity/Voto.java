package votacao.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Voto {

    @Id
    @Column
    private String id;

    @ManyToOne
    @JoinColumn(name = "ID_CAMPANHA")
    @JsonBackReference
    private Campanha campanha;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_CANDIDATO")
    @JsonBackReference
    private Candidato candidato;
}
