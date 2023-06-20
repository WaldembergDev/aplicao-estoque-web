package org.github.waldemberg.estoqueapp.model;

import javax.persistence.*;

@Entity
@Table(name = "tb_item_pedido")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_item")
    private long id;
    private int quantidade;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    public ItemPedido() {}

    public ItemPedido(int quantidade, Produto produto) {
        this.quantidade = quantidade;
        this.produto = produto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
