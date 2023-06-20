package org.github.waldemberg.estoqueapp.service;

import org.github.waldemberg.estoqueapp.dto.NovoPedidoDTO;
import org.github.waldemberg.estoqueapp.model.ItemPedido;
import org.github.waldemberg.estoqueapp.model.Pedido;
import org.github.waldemberg.estoqueapp.model.Status;
import org.github.waldemberg.estoqueapp.repository.PedidoRepository;
import org.github.waldemberg.estoqueapp.repository.ProdutoRepository;
import org.github.waldemberg.estoqueapp.util.EstoqueAbaixo;
import org.github.waldemberg.estoqueapp.util.UsuarioUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository repository;
    private final ProdutoRepository produtoRepository;

    public PedidoServiceImpl(PedidoRepository repository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
    }

    @Override
    public ArrayList<EstoqueAbaixo> salvar(NovoPedidoDTO pedido) {
        var novoPedido = new Pedido();
        var listaProdutosComEstoqueAbaixoDopedido = new ArrayList<EstoqueAbaixo>();

        novoPedido.setRequerente(UsuarioUtils.getUsuario());

        for (var produto : pedido.getProdutos().split(";")) {
            var produtoId = Long.parseLong(produto.split("#")[0]);
            var produtoQtd = Integer.parseInt(produto.split("#")[1]);

            var optProduto = produtoRepository.findById(produtoId);

            if (optProduto.isPresent()) {
                var p = optProduto.get();

                if (p.getQuantidade() >= produtoQtd) {
                    novoPedido.getItens().add(new ItemPedido(produtoQtd, p));
                } else {
                    listaProdutosComEstoqueAbaixoDopedido.add(new EstoqueAbaixo(p, produtoQtd));
                }
            }
        }

        if (listaProdutosComEstoqueAbaixoDopedido.isEmpty())
            repository.save(novoPedido);

        return listaProdutosComEstoqueAbaixoDopedido;
    }

    @Override
    public List<Pedido> listarTodos(int pagina, String status) {
        var p = of(pagina > 1 ? pagina - 1 : 0, 5);

        if (status == null || "".equalsIgnoreCase(status)) {
            return repository.findAll(p).getContent();
        }

        var filtrosEnum = converteFiltro(status);

        return repository.findByStatusIn(filtrosEnum, p);
    }

    private List<Status> converteFiltro(String filtros) {
        var filtroList = new LinkedList<Status>();

        for (var filtro : filtros.split(","))
            for (var status : Status.values())
                if (status.getStatus().equalsIgnoreCase(filtro)) {
                    filtroList.add(status);
                    break;
                }


        return filtroList;
    }

    @Override
    public void atualizar(Pedido pedido) {
        repository.save(pedido);
    }

    @Override
    public int totalPaginas() {
        return repository.findAll(of(0, 5)).getTotalPages();
    }

    @Override
    public void concluir(long id) {
        var pedido = repository.findById(id).orElseThrow();

        pedido.setStatus(Status.FINALIZADO);

        for (var item : pedido.getItens()) {

            item.getProduto().diminuiEstoque(item.getQuantidade());

            produtoRepository.save(item.getProduto());
        }
    }

    @Override
    public void cancelar(long id) {
        var pedido = repository.findById(id).orElseThrow();

        pedido.setStatus(Status.CANCELADO);

        repository.save(pedido);
    }

    @Override
    public List<Pedido> buscarMeusPedidos(int pagina, String status) {
        var pedidos = repository.findByRequerente(
                UsuarioUtils.getUsuario(),
                of(pagina > 0 ? pagina - 1 : 0, 5));

        if (status == null)
            return pedidos;

        var filtros = status.split(",");
        var pedidosFiltrados = new LinkedList<Pedido>();

        for (var filtro : filtros) {
            pedidosFiltrados.addAll(
                    pedidos.stream()
                            .filter(p -> filtro.equalsIgnoreCase(p.getStatus().getStatus()))
                            .collect(Collectors.toList())
            );
        }

        return pedidosFiltrados;
    }

    @Override
    public void aceitar(Long id) {
        var pedido = repository.findById(id).orElseThrow();

        pedido.setStatus(Status.FINALIZADO);

        pedido.setAtendente(UsuarioUtils.getUsuario());
        pedido.setDataFim(LocalDateTime.now());

        pedido.getItens().forEach(item -> {
            item.getProduto().diminuiEstoque(item.getQuantidade());

            produtoRepository.save(item.getProduto());
        });

        repository.save(pedido);
    }
}
