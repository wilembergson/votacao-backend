package votacao.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import votacao.backend.model.dto.Campanha.CampanhaAbertaDTO;
import votacao.backend.model.dto.Campanha.CampanhaInfoDTO;
import votacao.backend.model.dto.Campanha.NovaCampanhaDTO;
import votacao.backend.model.entity.Campanha;
import votacao.backend.service.CampanhaService;

import java.util.List;
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

    @GetMapping("/listar")
    public ResponseEntity listar(@RequestBody CampanhaAbertaDTO dto){
        List<Campanha> campanhas = this.campanhaService.listar(dto.votacao_aberta());
        return ResponseEntity.ok(campanhas);
    }

    @GetMapping("/{id}")
    public ResponseEntity obterPorId(@PathVariable String id){
        CampanhaInfoDTO campanha = this.campanhaService.obterPorId(id);
        return ResponseEntity.ok(campanha);
    }
}
