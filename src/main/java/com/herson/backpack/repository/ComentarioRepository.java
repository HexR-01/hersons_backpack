package com.herson.backpack.repository;

import com.herson.backpack.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByProyectoIdOrderByFechaCreacionDesc(Long proyectoId);
}
