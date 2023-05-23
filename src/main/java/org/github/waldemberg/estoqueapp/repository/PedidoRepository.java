package org.github.dumijdev.estoqueapp.repository;

import org.github.dumijdev.estoqueapp.model.Pedido;
import org.github.dumijdev.estoqueapp.model.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends PagingAndSortingRepository<Pedido, Long> {
    List<Pedido> findByRequerente(Usuario requerente);
}
