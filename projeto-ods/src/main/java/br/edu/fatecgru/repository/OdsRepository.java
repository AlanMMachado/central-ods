package br.edu.fatecgru.repository;

import br.edu.fatecgru.model.OdsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository


public interface OdsRepository extends JpaRepository<OdsEntity, Long> {
}

