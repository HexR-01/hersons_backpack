package com.herson.backpack.repository;

import com.herson.backpack.model.EnlaceProyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnlaceProyectoRepository extends JpaRepository<EnlaceProyecto, Long> {
    List<EnlaceProyecto> findByProyectoId(Long proyectoId);
}
