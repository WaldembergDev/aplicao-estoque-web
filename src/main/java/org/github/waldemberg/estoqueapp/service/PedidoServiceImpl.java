package org.github.dumijdev.estoqueapp.service;

import org.github.dumijdev.estoqueapp.dto.NovoPedidoDTO;
import org.github.dumijdev.estoqueapp.model.ItemPedido;
import org.github.dumijdev.estoqueapp.model.Pedido;
import org.github.dumijdev.estoqueapp.model.Usuario;
import org.github.dumijdev.estoqueapp.repository.PedidoRepository;
import org.github.dumijdev.estoqueapp.repository.ProdutoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository repository;
    private final ProdutoRepository produtoRepository;

    private final Usuario usuario;

    public PedidoServiceImpl(PedidoRepository repository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
        usuario = new Usuario();
    }

    @Override
    public void salvar(NovoPedidoDTO pedido) {
        var novoPedido = new Pedido();

        for (var produtoId : pedido.getProdutos()) {
            var optProduto = produtoRepository.findById(produtoId);

            if (optProduto.isPresent()) {
                novoPedido.getItens().add(new ItemPedido());
            }
        }
    }

    @Override
    public void atualizarPedido(Pedido pedido) {
        repository.save(pedido);
    }

    @Override
    public List<Pedido> listarTodos(int pagina) {
        return repository.findAll(PageRequest.of(pagina > 1 ? pagina - 1 : 0, 10)).getContent();
    }

    @Override
    public void deletar(Long id) {

    }

    @Override
    public void atualizar(Pedido pedido) {
        repository.save(pedido);
    }

    @Override
    public int totalPaginas() {
        return repository.findAll(PageRequest.of(0, 10)).getTotalPages();
    }
}
