package org.github.dumijdev.estoqueapp.repository;

import org.github.dumijdev.estoqueapp.model.Produto;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends PagingAndSortingRepository<Produto, Long> {
}