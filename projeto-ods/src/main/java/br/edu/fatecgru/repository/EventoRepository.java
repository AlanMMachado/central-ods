package br.edu.fatecgru.repository;

import br.edu.fatecgru.model.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface EventoRepository  extends JpaRepository<EventoEntity, Long> {
    @Query("SELECT e FROM EventoEntity e LEFT JOIN FETCH e.inscritos WHERE e.id = :id")
    Optional<EventoEntity> findByIdWithInscritos(@Param("id") Long id);

    @Query("SELECT e FROM EventoEntity e JOIN e.ods o WHERE o.id = :odsId")
    List<EventoEntity> findByOds(@Param("odsId") Long odsId);

    @Query("SELECT e FROM EventoEntity e LEFT JOIN FETCH e.ods WHERE e.id = :id")
    Optional<EventoEntity> findByIdWithOds(@Param("id") Long id);

    @Query("SELECT DISTINCT e FROM EventoEntity e LEFT JOIN FETCH e.ods")
    List<EventoEntity> findAllWithOds();

    // Busca eventos por ID com inscritos e ODS
    @Query("SELECT e FROM EventoEntity e LEFT JOIN FETCH e.ods LEFT JOIN FETCH e.inscritos WHERE e.id = :id")
    Optional<EventoEntity> findByIdCompleto(@Param("id") Long id);
}
