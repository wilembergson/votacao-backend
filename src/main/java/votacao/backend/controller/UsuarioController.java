package votacao.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import votacao.backend.model.dto.Usuario.LoginDTO;
import votacao.backend.model.dto.Usuario.NovoUsuarioDTO;
import votacao.backend.model.dto.Usuario.UsuarioInformacoesDTO;
import votacao.backend.security.TokenService;
import votacao.backend.service.UsuarioService;
import votacao.backend.utils.JsonRetorno;

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
        return ResponseEntity.ok(JsonRetorno.mensagem("Usuário cadastrado com sucesso."));
    }

    @GetMapping("/informacoes")
    public ResponseEntity obterInformacoes(@RequestHeader("Authorization") String token){
        String login = tokenService.getLogin(token);
        UsuarioInformacoesDTO informacoes = this.usuarioService.obterInformacoes(login);
        return ResponseEntity.ok(informacoes);
    }

    @PutMapping("/atualizar")
    public ResponseEntity atualizarAcesso(@RequestHeader("Authorization") String token, @RequestBody LoginDTO dto){
        String loginAtual = tokenService.getLogin(token);
        this.usuarioService.atualizarAcesso(loginAtual, dto);
        return ResponseEntity.ok(JsonRetorno.mensagem("Dados de acesso atualizados."));
    }
}
