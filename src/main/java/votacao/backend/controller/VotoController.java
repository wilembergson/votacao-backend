package votacao.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import votacao.backend.model.dto.Voto.ComprovanteVotoDTO;
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

    @PostMapping("/registrar/{senha}")
    public ResponseEntity votar(@RequestBody VotoDTO dto, @PathVariable String senha, @RequestHeader("Authorization") String token){
        String login = tokenService.getLogin(token);
        ComprovanteVotoDTO comprovante =  votoService.novoVoto(dto, login, senha);
        return ResponseEntity.status(HttpStatus.CREATED).body(comprovante);
    }

}
