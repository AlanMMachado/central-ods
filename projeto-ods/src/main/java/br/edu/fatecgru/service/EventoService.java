package br.edu.fatecgru.service;

import br.edu.fatecgru.DTO.EventoComODSDTO;
import br.edu.fatecgru.DTO.EventoDTO;
import br.edu.fatecgru.DTO.OdsResumoDTO;
import br.edu.fatecgru.DTO.UsuarioResumoDTO;
import br.edu.fatecgru.model.EventoEntity;
import br.edu.fatecgru.model.OdsEntity;
import br.edu.fatecgru.model.UsuarioEntity;
import br.edu.fatecgru.repository.EventoRepository;
import br.edu.fatecgru.repository.OdsRepository;
import br.edu.fatecgru.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OdsRepository odsRepository;

    @Transactional
    public EventoDTO criarEventoComODS(EventoComODSDTO eventoDTO, Long hostId) {
        // Validação básica
        if (eventoDTO.getOdsIds() == null || eventoDTO.getOdsIds().isEmpty()) {
            throw new IllegalArgumentException("Pelo menos uma ODS deve ser informada");
        }

        // Valida e obtém as ODS
        Set<OdsEntity> odsEntities = validarEObterODS(eventoDTO.getOdsIds());

        // Obtém o host
        UsuarioEntity host = usuarioRepository.findById(hostId)
                .orElseThrow(() -> new EntityNotFoundException("Host não encontrado"));

        // Cria o evento
        EventoEntity evento = new EventoEntity();
        evento.setTitulo(eventoDTO.getTitulo());
        evento.setDescricao(eventoDTO.getDescricao());
        evento.setLocalizacao(eventoDTO.getLocalizacao());
        evento.setData(eventoDTO.getData());
        evento.setUsuarioHost(host);

        // Associa as ODS
        odsEntities.forEach(evento::addOds);

        // Salva e retorna
        EventoEntity eventoSalvo = eventoRepository.save(evento);
        return toDTO(eventoSalvo);
    }

    private Set<OdsEntity> validarEObterODS(Set<Long> odsIds) {
        // Valida quantidade
        if (odsIds.size() > 3) {
            throw new IllegalArgumentException("Máximo de 3 ODS por evento");
        }

        // Obtém as ODS do banco e converte para Set
        Set<OdsEntity> odsEntities = new HashSet<>(odsRepository.findAllById(odsIds));

        // Verifica se todas foram encontradas
        if (odsEntities.size() != odsIds.size()) {
            throw new EntityNotFoundException("Uma ou mais ODS não foram encontradas");
        }

        // Verifica se são ODS válidas (IDs entre 1 e 17)
        if (odsEntities.stream().anyMatch(ods -> ods.getId() < 1 || ods.getId() > 17)) {
            throw new IllegalArgumentException("IDs de ODS devem estar entre 1 e 17");
        }

        return odsEntities;
    }



    @Transactional
    public EventoDTO atualizarODSEvento(Long eventoId, Set<Long> odsIds) {
        validarODS(odsIds);
        EventoEntity evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado"));
        Set<OdsEntity> odsEntities = obterODSValidas(odsIds);

        // Limpa as ODS atuais mantendo a consistência
        evento.getOds().forEach(ods -> ods.getEventos().remove(evento));
        evento.getOds().clear();

        // Adiciona as novas ODS mantendo a consistência
        odsEntities.forEach(evento::addOds);

        EventoEntity eventoAtualizado = eventoRepository.save(evento);
        return toDTO(eventoAtualizado);
    }

    private void validarODS(Set<Long> odsIds) {
        if (odsIds == null || odsIds.isEmpty() || odsIds.size() > 3) {
            throw new IllegalArgumentException("O evento deve estar relacionado a 1 a 3 ODS");
        }
    }

    private Set<OdsEntity> obterODSValidas(Set<Long> odsIds) {
        Set<OdsEntity> odsEntities = odsRepository.findAllById(odsIds).stream()
                .filter(ods -> ods.getId() >= 1 && ods.getId() <= 17)
                .collect(Collectors.toSet());

        if (odsEntities.size() != odsIds.size()) {
            throw new EntityNotFoundException("Uma ou mais ODS não foram encontradas ou são inválidas");
        }
        return odsEntities;
    }

    public List<EventoDTO> listarPorODS(Long odsId) {
        OdsEntity ods = odsRepository.findById(odsId)
                .orElseThrow(() -> new EntityNotFoundException("ODS não encontrada"));

        return eventoRepository.findByOds(ods.getId()).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    private EventoDTO toDTO(EventoEntity evento) {
        UsuarioResumoDTO hostDTO = new UsuarioResumoDTO(
                evento.getUsuarioHost().getId(),
                evento.getUsuarioHost().getNome(),
                evento.getUsuarioHost().getEmail()
        );

        Set<UsuarioResumoDTO> inscritosDTO = evento.getInscritos().stream()
                .map(usuario -> new UsuarioResumoDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail()
                ))
                .collect(Collectors.toSet());

        Set<OdsResumoDTO> odsDTO = evento.getOds().stream()
                .map(ods -> new OdsResumoDTO(ods.getId(), ods.getTitulo()))
                .collect(Collectors.toSet());

        return new EventoDTO(
                evento.getId(),
                evento.getTitulo(),
                evento.getDescricao(),
                evento.getLocalizacao(),
                hostDTO,
                inscritosDTO,
                evento.getData(),
                odsDTO
        );
    }


    // Método auxiliar para converter DTO para Entity (para criação)
    private EventoEntity toEntity(EventoDTO eventoDTO) {
        EventoEntity evento = new EventoEntity();
        evento.setTitulo(eventoDTO.getTitulo());
        evento.setDescricao(eventoDTO.getDescricao());
        evento.setLocalizacao(eventoDTO.getLocalizacao());
        evento.setData(eventoDTO.getData());

        // O host deve ser definido separadamente no método de criação
        return evento;
    }

    public EventoDTO criar(EventoDTO eventoDTO, Long hostId) {
        UsuarioEntity host = usuarioRepository.findById(hostId)
                .orElseThrow(() -> new EntityNotFoundException("Host não encontrado"));

        EventoEntity evento = toEntity(eventoDTO);
        evento.setUsuarioHost(host);

        EventoEntity eventoSalvo = eventoRepository.save(evento);
        return toDTO(eventoSalvo);
    }


    public EventoDTO inscrever(Long eventoId, Long usuarioId) {
        EventoEntity evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado"));

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (evento.getUsuarioHost().equals(usuario)) {
            throw new IllegalArgumentException("O host não pode se inscrever no próprio evento");
        }

        if (evento.getInscritos().contains(usuario)) {
            throw new IllegalStateException("Usuário já está inscrito neste evento");
        }

        evento.addInscrito(usuario);
        EventoEntity eventoAtualizado = eventoRepository.save(evento);

        return toDTO(eventoAtualizado);
    }

    public EventoDTO ler(Long id) {
        EventoEntity evento = eventoRepository.findByIdWithOds(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado"));
        return toDTO(evento);
    }

    public List<EventoDTO> listarTodos() {
        List<EventoEntity> eventos = eventoRepository.findAllWithOds();  // Novo método no repository
        return eventos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public EventoDTO editar(Long id, EventoDTO eventoDTO) {
        EventoEntity eventoExistente = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado"));

        eventoExistente.setTitulo(eventoDTO.getTitulo());
        eventoExistente.setDescricao(eventoDTO.getDescricao());
        eventoExistente.setLocalizacao(eventoDTO.getLocalizacao());
        eventoExistente.setData(eventoDTO.getData());

        // Não altera as ODS aqui - use atualizarODSEvento para isso
        EventoEntity eventoAtualizado = eventoRepository.save(eventoExistente);
        return toDTO(eventoAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        EventoEntity evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado"));

        // Remove as referências bidirecionais antes de deletar
        evento.getOds().forEach(ods -> ods.getEventos().remove(evento));
        evento.getInscritos().forEach(usuario -> usuario.getEventos().remove(evento));

        eventoRepository.delete(evento);
    }

    public int quantidadeInscritos(Long eventoId) {
        EventoEntity evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado"));
        return evento.getInscritos().size();
    }

    public Set<UsuarioResumoDTO> listarInscritos(Long eventoId) {
        EventoEntity evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado"));

        return evento.getInscritos().stream()
                .map(usuario -> new UsuarioResumoDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail()
                ))
                .collect(Collectors.toSet());
    }


    public EventoDTO cancelarInscricao(Long eventoId, Long usuarioId) {
        EventoEntity evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado"));

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (!evento.getInscritos().contains(usuario)) {
            throw new IllegalStateException("Usuário não está inscrito neste evento");
        }

        evento.removeInscrito(usuario);
        EventoEntity eventoAtualizado = eventoRepository.save(evento);

        return toDTO(eventoAtualizado);
    }
}