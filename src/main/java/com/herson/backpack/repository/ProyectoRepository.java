package com.herson.backpack.repository;

import com.herson.backpack.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    List<Proyecto> findByUsuarioIdOrderByFechaActualizacionDesc(Long usuarioId);

    // Devuelve proyectos donde el usuario es dueño O donde es colaborador (sale como suyo)
    @Query("SELECT DISTINCT p FROM Proyecto p LEFT JOIN p.colaboradores c " +
           "WHERE p.usuario.id = :usuarioId OR c.id = :usuarioId " +
           "ORDER BY p.fechaActualizacion DESC")
    List<Proyecto> findMisProyectosYColaboraciones(@Param("usuarioId") Long usuarioId);

    List<Proyecto> findByTipoIgnoreCaseOrderByFechaActualizacionDesc(String tipo);

    @Query("SELECT p FROM Proyecto p WHERE " +
           "LOWER(p.titulo) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.tipo) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.descripcionCorta) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.tecnologias) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.usuario.username) LIKE LOWER(CONCAT('%', :q, '%')) " +
           "ORDER BY p.fechaActualizacion DESC")
    List<Proyecto> buscarPorTermino(@Param("q") String query);

    List<Proyecto> findAllByOrderByFechaActualizacionDesc();
}
