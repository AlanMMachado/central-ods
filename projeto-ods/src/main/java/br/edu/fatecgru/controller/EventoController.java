package br.edu.fatecgru.controller;

import br.edu.fatecgru.DTO.EventoComODSDTO;
import br.edu.fatecgru.DTO.EventoDTO;
import br.edu.fatecgru.model.EventoEntity;
import br.edu.fatecgru.model.OdsEntity;
import br.edu.fatecgru.repository.EventoRepository;
import br.edu.fatecgru.repository.OdsRepository;
import br.edu.fatecgru.service.EventoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;


    @Autowired
    private EventoRepository eventoRepository;


    @Autowired
    private OdsRepository odsRepository;

    //Obs: O hostId deve ser enviado como query parameter: /eventos?hostId=1
    @PostMapping
    public ResponseEntity<?> criar(
            @RequestBody EventoComODSDTO eventoDTO,
            @RequestParam Long hostId) {
        try {
            EventoDTO novoEvento = eventoService.criarEventoComODS(eventoDTO, hostId);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoEvento);
        } catch (IllegalArgumentException | EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Erro ao criar evento");
        }
    }


    @PostMapping("/{eventoId}/inscrever/{usuarioId}")
    public ResponseEntity<?> inscreverUsuario(@PathVariable Long eventoId, @PathVariable Long usuarioId) {
        try {
            EventoDTO evento = eventoService.inscrever(eventoId, usuarioId);
            return ResponseEntity.ok(evento);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar inscrição");
        }
    }

    @GetMapping("/{id}/com-inscritos")
    public ResponseEntity<?> getEventoComInscritos(@PathVariable Long id) {
        try {
            EventoDTO evento = eventoService.ler(id);
            return ResponseEntity.ok(evento);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<EventoDTO>> listarTodos() {
        return ResponseEntity.ok(eventoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ler(@PathVariable Long id) {
        try {
            EventoDTO evento = eventoService.ler(id);
            return ResponseEntity.ok(evento);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody EventoDTO eventoDTO, @PathVariable Long id) {
        try {
            EventoDTO eventoAtualizado = eventoService.editar(id, eventoDTO);
            return ResponseEntity.ok(eventoAtualizado);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            eventoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    //NÃO ESTÁ FUNCIONANDO AINDA
    @GetMapping("/{id}/quantidade-inscritos")
    public ResponseEntity<?> quantidadeInscritos(@PathVariable Long id) {
        try {
            int quantidade = eventoService.quantidadeInscritos(id);
            return ResponseEntity.ok(quantidade);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/{id}/inscritos")
    public ResponseEntity<?> listarInscritos(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(eventoService.listarInscritos(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{eventoId}/inscricao/{usuarioId}")
    public ResponseEntity<?> cancelarInscricao(
            @PathVariable Long eventoId,
            @PathVariable Long usuarioId) {

        try {
            EventoDTO evento = eventoService.cancelarInscricao(eventoId, usuarioId);
            return ResponseEntity.ok(evento);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cancelar inscrição");
        }
    }



    @PostMapping("/com-ods")
    public ResponseEntity<?> criarComODS(
            @RequestBody EventoComODSDTO eventoDTO,
            @RequestParam Long hostId) {
        try {
            EventoDTO evento = eventoService.criarEventoComODS(eventoDTO, hostId);
            return ResponseEntity.status(HttpStatus.CREATED).body(evento);
        } catch (IllegalArgumentException | EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


        @PutMapping("/{eventoId}/ods")
        public ResponseEntity<?> atualizarODSEvento(
                @PathVariable Long eventoId,
                @RequestBody Set<Long> odsIds) {
            try {
                EventoDTO evento = eventoService.atualizarODSEvento(eventoId, odsIds);
                return ResponseEntity.ok(evento);
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest().body(ex.getMessage());
            } catch (EntityNotFoundException ex) {
                return ResponseEntity.notFound().build();
            }
        }

        @GetMapping("/por-ods/{odsId}")
        public ResponseEntity<?> listarPorODS(@PathVariable Long odsId) {
            try {
                List<EventoDTO> eventos = eventoService.listarPorODS(odsId);
                return ResponseEntity.ok(eventos);
            } catch (EntityNotFoundException ex) {
                return ResponseEntity.notFound().build();
            }
    }

}