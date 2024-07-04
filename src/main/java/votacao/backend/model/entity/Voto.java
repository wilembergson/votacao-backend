package votacao.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voto {

    @Id
    @Column
    private String id;

    @Column
    private String usuario_hash;

    @ManyToOne
    @JoinColumn(name = "ID_CAMPANHA")
    @JsonBackReference
    private Campanha campanha;

    @ManyToOne
    @JoinColumn(name = "ID_CANDIDATO")
    @JsonBackReference
    private Candidato candidato;
}
