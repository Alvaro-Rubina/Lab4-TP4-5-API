package org.spdgrupo.lab4tp45api.repository;

import org.spdgrupo.lab4tp45api.model.entity.Instrumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentoRepository extends JpaRepository<Instrumento, Long> {
}
