package votacao.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import votacao.backend.model.dto.NovoUsuarioDTO;
import votacao.backend.service.UsuarioService;

import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity novoUsuario(@RequestBody NovoUsuarioDTO dto){
        this.usuarioService.novoUsuario(dto);
        return ResponseEntity.ok().body(Map.of("mensagem", "Usuário cadastrado com sucesso."));
    }
}
