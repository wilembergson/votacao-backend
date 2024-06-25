package votacao.backend.repository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import votacao.backend.model.dto.Campanha.CampanhaDTO;
import votacao.backend.model.dto.Campanha.CampanhaInfoDTO;
import votacao.backend.model.entity.Campanha;
import votacao.backend.service.CampanhaService;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, String> {

    Optional<Campanha> findByTitulo(String titulo);
}