package br.edu.fatecgru.service;

import br.edu.fatecgru.DTO.EventoResumoDTO;
import br.edu.fatecgru.DTO.UsuarioDTO;
import br.edu.fatecgru.DTO.UsuarioResumoDTO;
import br.edu.fatecgru.model.EventoEntity;
import br.edu.fatecgru.model.UsuarioEntity;
import br.edu.fatecgru.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoService eventoService; // Para conversão de Eventos para DTOs

    // Método auxiliar para converter Entity para DTO completo
    private UsuarioDTO toDTO(UsuarioEntity usuario) {
        Set<EventoResumoDTO> eventosDTO = usuario.getEventos().stream()
                .map(evento -> new EventoResumoDTO(
                        evento.getId(),
                        evento.getTitulo(),
                        evento.getLocalizacao(),
                        evento.getData()
                ))
                .collect(Collectors.toSet());

        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getCidade(),
                usuario.getSenha(),
                eventosDTO
        );
    }

    // Método auxiliar para converter Entity para DTO resumido
    private UsuarioResumoDTO toResumoDTO(UsuarioEntity usuario) {
        return new UsuarioResumoDTO( usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    // Método auxiliar para converter DTO para Entity
    private UsuarioEntity toEntity(UsuarioDTO usuarioDTO) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefone(usuarioDTO.getTelefone());
        usuario.setCidade(usuarioDTO.getCidade());
        usuario.setSenha(usuarioDTO.getSenha()); // Senha deve ser hasheada antes
        return usuario;
    }

    public UsuarioDTO criar(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getId() != null) {
            throw new IllegalArgumentException("Corpo do usuário não deve conter ID");
        }
        UsuarioEntity usuario = toEntity(usuarioDTO);
        UsuarioEntity usuarioSalvo = usuarioRepository.save(usuario);

        return toDTO(usuarioSalvo);
    }

    public UsuarioDTO ler(Long id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return toDTO(usuario);
    }

    public class SenhaIncorretaException extends RuntimeException {
        public SenhaIncorretaException(String mensagem) {
            super(mensagem);
        }
    }


    public UsuarioDTO loginUsuario(String email, String senha) {
        UsuarioEntity usuario = usuarioRepository.login(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (senha != null && senha.equals(usuario.getSenha())) {
            return toDTO(usuario);
        } else {
            throw new SenhaIncorretaException("Senha incorreta");
        }
    }


    public List<UsuarioResumoDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::toResumoDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO editar(Long id, UsuarioDTO usuarioDTO) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }

        // Aqui deveria haver lógica para hashear a senha se for fornecida
        UsuarioEntity usuarioExistente = usuarioRepository.findById(id).get();

        // Atualiza apenas os campos permitidos
        usuarioExistente.setNome(usuarioDTO.getNome());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setTelefone(usuarioDTO.getTelefone());
        usuarioExistente.setCidade(usuarioDTO.getCidade());

        // Atualiza senha apenas se for fornecida no DTO
        if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isEmpty()) {
            usuarioExistente.setSenha(usuarioDTO.getSenha()); // Deveria ser hasheada
        }

        UsuarioEntity usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        return toDTO(usuarioAtualizado);
    }

    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public Set<EventoResumoDTO> eventosAssociados(Long id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return usuario.getEventos().stream()
                .map(evento -> new EventoResumoDTO(
                        evento.getId(),
                        evento.getTitulo(),
                        evento.getLocalizacao(),
                        evento.getData()
                ))
                .collect(Collectors.toSet());
    }
}