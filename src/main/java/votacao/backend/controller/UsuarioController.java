package votacao.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import votacao.backend.model.dto.NovoUsuarioDTO;
import votacao.backend.model.dto.UsuarioInformacoesDTO;
import votacao.backend.security.TokenService;
import votacao.backend.service.UsuarioService;

import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/cadastrar")
    public ResponseEntity novoUsuario(@RequestBody NovoUsuarioDTO dto){
        this.usuarioService.novoUsuario(dto);
        return ResponseEntity.ok().body(Map.of("mensagem", "Usuário cadastrado com sucesso."));
    }

    @GetMapping("/informacoes")
    public ResponseEntity obterInformacoes(@RequestHeader("Authorization") String token){
        Long cpf = tokenService.getUserCpf(token);
        UsuarioInformacoesDTO informacoes = this.usuarioService.obterInformacoes(cpf);
        return ResponseEntity.ok().body(informacoes);
    }
}
