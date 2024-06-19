package votacao.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import votacao.backend.model.entity.Campanha;

@Repository
public interface CampanhaReposritory extends JpaRepository<Campanha, String> {
}
