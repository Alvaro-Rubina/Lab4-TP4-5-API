package org.spdgrupo.lab4tp45api.repository;

import org.spdgrupo.lab4tp45api.model.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    @Query("SELECT d.instrumento.instrumento, COUNT(d) FROM DetallePedido d GROUP BY d.instrumento.instrumento")
    List<Object[]> countPedidosByInstrumento();

}
