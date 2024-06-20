package votacao.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import votacao.backend.model.dto.Campanha.NovaCampanhaDTO;
import votacao.backend.service.CampanhaService;

import java.util.Map;

@RestController
@RequestMapping("/campanha")
public class CampanhaController {

    @Autowired
    private CampanhaService campanhaService;

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarCampanha(@RequestBody NovaCampanhaDTO dto){
        this.campanhaService.novaCampanha(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Nova campanha registrada."));
    }
}
