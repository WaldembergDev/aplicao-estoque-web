package org.github.dumijdev.estoqueapp.dto;

import java.util.List;

public class NovoPedidoDTO {
    private String usernameRequerente;
    private List<Long> produtos;

    public String getUsernameRequerente() {
        return usernameRequerente;
    }

    public void setUsernameRequerente(String usernameRequerente) {
        this.usernameRequerente = usernameRequerente;
    }

    public List<Long> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Long> produtos) {
        this.produtos = produtos;
    }
}
