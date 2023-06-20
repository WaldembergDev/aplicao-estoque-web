package org.github.waldemberg.estoqueapp.dto;

import java.util.List;

public class NovoPedidoDTO {
    private String produtos;

    public String getProdutos() {
        return produtos;
    }

    public void setProdutos(String produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return "NovoPedidoDTO{" +
                "produtos='" + produtos + '\'' +
                '}';
    }
}
