package org.github.dumijdev.estoqueapp.service;

import org.github.dumijdev.estoqueapp.dto.NovoProdutoDTO;
import org.github.dumijdev.estoqueapp.model.Produto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProdutoService {
    void salvar(NovoProdutoDTO produto);
    List<Produto> listarTodos(int page);

    int totalPaginas();

    void atualizar(Produto produto);

    void deletar(Long id);
}
