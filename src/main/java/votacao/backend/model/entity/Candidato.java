package votacao.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidato {

    @Id
    private String id;

    @Column
    private String nome;

    @Column
    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "ID_CAMPANHA")
    @JsonBackReference
    private Campanha campanha;
}
