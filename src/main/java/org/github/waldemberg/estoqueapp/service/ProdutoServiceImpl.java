package org.github.dumijdev.estoqueapp.service;

import org.github.dumijdev.estoqueapp.dto.NovoProdutoDTO;
import org.github.dumijdev.estoqueapp.model.Produto;
import org.github.dumijdev.estoqueapp.repository.ProdutoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
    public List<Produto> listarTodos(int page) {
        var pagina = PageRequest.of(page > 1 ? page - 1 : 0, 10);
        return repository.findAll(pagina).getContent();
    }

    @Override
    public int totalPaginas() {
        return repository.findAll(PageRequest.of(0, 10)).getTotalPages();
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
