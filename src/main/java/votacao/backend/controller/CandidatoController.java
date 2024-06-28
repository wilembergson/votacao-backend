package votacao.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import votacao.backend.model.dto.Candidato.BuscarCandidatoDTO;
import votacao.backend.model.dto.Candidato.CandidatoDTO;
import votacao.backend.model.entity.Candidato;
import votacao.backend.service.CandidatoService;
import votacao.backend.utils.JsonRetorno;

@RestController
@RequestMapping("/candidato")
public class CandidatoController {

    @Autowired
    private CandidatoService candidatoService;

    @PostMapping("/registrar-em-campanha/{campanhaId}")
    public ResponseEntity registrarCandidato(@PathVariable String campanhaId, @RequestBody CandidatoDTO dto){
        candidatoService.novoCandidato(campanhaId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(JsonRetorno.mensagem("Candidato registrado com sucesso!"));
    }

    @GetMapping("/buscar-por-numero")
    public ResponseEntity buscarPorNumero(@RequestBody BuscarCandidatoDTO dto){
        Candidato candidato = candidatoService.buscarPorNumero(dto);
        return ResponseEntity.ok(candidato);
    }
}
