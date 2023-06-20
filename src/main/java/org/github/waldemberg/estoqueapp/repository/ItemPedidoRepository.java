package org.github.waldemberg.estoqueapp.repository;

import org.github.waldemberg.estoqueapp.model.ItemPedido;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends PagingAndSortingRepository<ItemPedido, Long> {
}