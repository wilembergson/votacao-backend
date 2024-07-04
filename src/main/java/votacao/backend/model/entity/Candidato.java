package votacao.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Voto> votos;

    public Integer getTotal_votos(){
        return this.votos.size();
    }
}
