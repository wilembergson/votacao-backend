package votacao.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import votacao.backend.model.entity.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, String> {
}
