package br.edu.fatecgru.controller;

import br.edu.fatecgru.DTO.UsuarioDTO;
import br.edu.fatecgru.DTO.UsuarioResumoDTO;
import br.edu.fatecgru.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO usuarioCriado = usuarioService.criar(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar usuário");
        }
    }

    @GetMapping("/{id}/com-eventos")
    public ResponseEntity<?> getUsuarioComEventos(@PathVariable Long id) {
        try {
            UsuarioDTO usuario = usuarioService.ler(id);
            return ResponseEntity.ok(usuario);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao carregar usuário com eventos");
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResumoDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ler(@PathVariable Long id) {
        try {
            UsuarioDTO usuario = usuarioService.ler(id);
            return ResponseEntity.ok(usuario);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao carregar usuário");
        }
    }

    @GetMapping("/login/{email}/{senha}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String senha) {
        try {
            UsuarioDTO usuario = usuarioService.loginUsuario(email, senha);
            return ResponseEntity.ok(usuario);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (UsuarioService.SenhaIncorretaException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao carregar usuário");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody UsuarioDTO usuarioDTO, @PathVariable Long id) {
        try {
            UsuarioDTO usuarioAtualizado = usuarioService.editar(id, usuarioDTO);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar usuário");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            usuarioService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar usuário");
        }
    }
}