package org.github.dumijdev.estoqueapp.repository;

import org.github.dumijdev.estoqueapp.model.ItemPedido;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends PagingAndSortingRepository<ItemPedido, Long> {
}