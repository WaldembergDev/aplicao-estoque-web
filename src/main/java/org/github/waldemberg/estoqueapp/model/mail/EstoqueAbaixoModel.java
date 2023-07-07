package org.github.waldemberg.estoqueapp.model.mail;

import org.github.waldemberg.estoqueapp.model.Produto;

import java.util.List;

public class EstoqueAbaixoModel {
    private final List<Produto> produtos;

    public EstoqueAbaixoModel(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}
