package votacao.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import votacao.backend.model.entity.Candidato;

import java.util.Optional;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, String> {

    Optional<Candidato> findByNumero(Integer numero);
}
