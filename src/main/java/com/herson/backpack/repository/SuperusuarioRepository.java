package com.herson.backpack.repository;

import com.herson.backpack.model.Superusuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperusuarioRepository extends JpaRepository<Superusuario, Long> {
    boolean existsByUsuarioId(Long usuarioId);
}
