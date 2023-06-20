package org.github.waldemberg.estoqueapp.util;

import org.github.waldemberg.estoqueapp.model.Produto;

public class EstoqueAbaixo {
    private final Produto produto;
    private final int quantidade;

    public EstoqueAbaixo(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public String getMensagem() {
        return String.format("O produto %s não pode atender a este pedido com %d unidades", produto.getNome(), quantidade);
    }
}
