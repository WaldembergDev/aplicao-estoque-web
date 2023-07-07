package org.github.waldemberg.estoqueapp.service;

import org.github.waldemberg.estoqueapp.dto.NovoPedidoDTO;
import org.github.waldemberg.estoqueapp.exceptions.EstoqueInsuficienteException;
import org.github.waldemberg.estoqueapp.model.Pedido;
import org.github.waldemberg.estoqueapp.util.ErroGeral;
import org.github.waldemberg.estoqueapp.util.EstoqueAbaixo;

import java.util.ArrayList;
import java.util.List;

public interface PedidoService {
    ArrayList<EstoqueAbaixo> salvar(NovoPedidoDTO pedido);

    List<Pedido> listarTodos(int pagina, String status);

    void atualizar(Pedido pedido);

    int totalPaginas();

    void concluir(long id);

    void cancelar(long id);

    List<Pedido> buscarMeusPedidos(int pagina, String status);

    void aceitar(Long id) throws EstoqueInsuficienteException;
}
