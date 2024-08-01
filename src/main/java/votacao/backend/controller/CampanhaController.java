package votacao.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import votacao.backend.model.dto.Campanha.CampanhaAbertaDTO;
import votacao.backend.model.dto.Campanha.CampanhaDTO;
import votacao.backend.model.dto.Campanha.CampanhaInfoDTO;
import votacao.backend.model.entity.Campanha;
import votacao.backend.service.CampanhaService;
import votacao.backend.utils.JsonRetorno;

import java.util.List;

@RestController
@RequestMapping("/campanha")
public class CampanhaController {

    @Autowired
    private CampanhaService campanhaService;

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarCampanha(@RequestBody CampanhaDTO dto){
        this.campanhaService.novaCampanha(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(JsonRetorno.mensagem("Nova campanha registrada."));
    }

    @GetMapping("/listar")
    public ResponseEntity listar(){
        List<CampanhaInfoDTO> campanhas = this.campanhaService.listar();
        return ResponseEntity.ok(campanhas);
    }

    @GetMapping("/{id}")
    public ResponseEntity obterPorId(@PathVariable String id){
        Campanha campanha = this.campanhaService.buscarPorId(id);
        return ResponseEntity.ok(campanha);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity atualizar(@PathVariable String id, @RequestBody CampanhaDTO dto){
        this.campanhaService.atualizar(id, dto);
        return ResponseEntity.ok(JsonRetorno.mensagem("As informações da campanha foram atualizadas."));
    }
}
