package org.spdgrupo.lab4tp45api.repository;

import org.spdgrupo.lab4tp45api.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByFechaPedidoBetween(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT FUNCTION('DATE_FORMAT', p.fechaPedido, '%Y-%m') AS mesAnio, COUNT(p) " +
            "FROM Pedido p " +
            "GROUP BY FUNCTION('DATE_FORMAT', p.fechaPedido, '%Y-%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', p.fechaPedido, '%Y-%m')")
    List<Object[]> countPedidosGroupedByMesAnio();


}
