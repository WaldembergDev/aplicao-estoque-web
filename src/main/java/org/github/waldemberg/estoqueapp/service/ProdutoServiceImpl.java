package org.github.waldemberg.estoqueapp.service;

import org.github.waldemberg.estoqueapp.dto.NovoProdutoDTO;
import org.github.waldemberg.estoqueapp.model.Produto;
import org.github.waldemberg.estoqueapp.repository.ProdutoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoServiceImpl(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void salvar(NovoProdutoDTO produto) {

        var novoProduto = new Produto(
                produto.getNome(),
                produto.getQuantidade(),
                produto.getQuantidadeMinima()
        );

        repository.save(novoProduto);
    }

    @Override
    public List<Produto> listarTodos(int page, String status) {
        var pagina = PageRequest.of(page > 1 ? page - 1 : 0, 5);

        if (status == null || "".equalsIgnoreCase(status))
            return repository.findAll(pagina).getContent();

        LinkedList<Produto> produtos = new LinkedList<>();

        for (var s1 : status.split(",")) {
            produtos.addAll(
                    status.equalsIgnoreCase("alerta") ?
                    repository.buscarProdutosEmAtencao(pagina).getContent() :
                    repository.buscarProdutosCriticos(pagina).getContent()
            );
        }

        return produtos;
    }

    @Override
    public int totalPaginas(String status) {
        var pagina = PageRequest.of(0, 5);

        if (status == null || "".equalsIgnoreCase(status))
            return repository.findAll(pagina).getTotalPages();

        return status.equalsIgnoreCase("alerta") ?
                repository.buscarProdutosEmAtencao(pagina).getTotalPages() :
                repository.buscarProdutosCriticos(pagina).getTotalPages();
    }

    @Override
    public void atualizar(Produto produto) {
        repository.save(produto);
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
