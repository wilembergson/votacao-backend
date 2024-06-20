package votacao.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import votacao.backend.model.entity.Campanha;

import java.util.Optional;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, String> {

    Optional<Campanha> findByTitulo(String titulo);
}
