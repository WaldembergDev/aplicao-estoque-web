package org.github.waldemberg.estoqueapp.repository;

import org.github.waldemberg.estoqueapp.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends PagingAndSortingRepository<Produto, Long> {
    @Query("select p from Produto p where p.quantidade - p.quantidadeMinima <= 4 and p.quantidade - p.quantidadeMinima >= 1")
    Page<Produto> buscarProdutosEmAtencao(Pageable page);

    @Query("select p from Produto p where p.quantidade - p.quantidadeMinima < 1")
    Page<Produto> buscarProdutosCriticos(Pageable page);

    @Query("select p from Produto p where p.quantidade > ?1")
    List<Produto> buscarProdutosComEstoqueMaiorQue(int quantidade);


}