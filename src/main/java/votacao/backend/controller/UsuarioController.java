package votacao.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import votacao.backend.model.dto.Usuario.LoginDTO;
import votacao.backend.model.dto.Usuario.NovoUsuarioDTO;
import votacao.backend.model.dto.Usuario.UsuarioInformacoesDTO;
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

    @PutMapping("/atualizar")
    public ResponseEntity atualizarAcesso(@RequestHeader("Authorization") String token, @RequestBody LoginDTO dto){
        Long cpf = tokenService.getUserCpf(token);
        this.usuarioService.atualizarAcesso(cpf, dto);
        return ResponseEntity.ok().body(Map.of("mensagem", "Dados de acesso atualizados."));
    }
}
