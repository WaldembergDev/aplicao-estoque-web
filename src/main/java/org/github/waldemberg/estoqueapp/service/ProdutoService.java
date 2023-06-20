package org.github.waldemberg.estoqueapp.service;

import org.github.waldemberg.estoqueapp.dto.NovoProdutoDTO;
import org.github.waldemberg.estoqueapp.model.Produto;

import java.util.List;

public interface ProdutoService {
    void salvar(NovoProdutoDTO produto);
    List<Produto> listarTodos(int page, String status);

    int totalPaginas(String status);

    void atualizar(Produto produto);

    void deletar(Long id);
}
