package org.github.dumijdev.estoqueapp.service;

import org.github.dumijdev.estoqueapp.dto.NovoPedidoDTO;
import org.github.dumijdev.estoqueapp.model.Pedido;

import java.util.List;

public interface PedidoService {
    void salvar(NovoPedidoDTO pedido);
    void atualizarPedido(Pedido pedido);
    List<Pedido> listarTodos(int pagina);

    void deletar(Long id);

    void atualizar(Pedido pedido);

    int totalPaginas();
}
