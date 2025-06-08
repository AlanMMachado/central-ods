package br.edu.fatecgru.repository;

import br.edu.fatecgru.model.EventoEntity;
import br.edu.fatecgru.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    // Busca eventos por ID com inscritos e ODS
    @Query("SELECT u FROM UsuarioEntity u WHERE u.email = :email")
    Optional<UsuarioEntity> login(@Param("email") String email);

    @Query("SELECT u.senha FROM UsuarioEntity u WHERE u.email = :email")
    Optional<UsuarioEntity> loginSenha(@Param("email") String email);



}
