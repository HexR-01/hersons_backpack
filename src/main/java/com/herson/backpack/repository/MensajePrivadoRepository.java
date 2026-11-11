package com.herson.backpack.repository;

import com.herson.backpack.model.MensajePrivado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MensajePrivadoRepository extends JpaRepository<MensajePrivado, Long> {
    List<MensajePrivado> findByDestinatarioIdOrderByFechaEnvioDesc(Long destinatarioId);
    List<MensajePrivado> findByRemitenteIdOrderByFechaEnvioDesc(Long remitenteId);
}
