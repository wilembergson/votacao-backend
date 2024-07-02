package votacao.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import votacao.backend.model.dto.Voto.VotoDTO;
import votacao.backend.security.TokenService;
import votacao.backend.service.VotoService;
import votacao.backend.utils.JsonRetorno;

@RestController
@RequestMapping("/voto")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/novo")
    public ResponseEntity votar(@RequestBody VotoDTO dto, @RequestHeader("Authorization") String token){
        Long cpf = tokenService.getUserCpf(token);
        votoService.novoVoto(dto, cpf);
        return ResponseEntity.status(HttpStatus.CREATED).body(JsonRetorno.mensagem("Voto registrado."));
    }

}
