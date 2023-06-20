package org.github.waldemberg.estoqueapp.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_produto")
    private long id;
    @NotEmpty
    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
    private String nome;
    @Min(0)
    private int quantidade;
    @Min(0)
    private int quantidadeMinima;

    public Produto() {
    }

    public Produto(String nome, int quantidade, int quantidadeMinima) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.quantidadeMinima = quantidadeMinima;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void diminuiEstoque(int qtd) {
        setQuantidade(Math.max(quantidade - qtd, 0));
    }

    public void adicionaEstoque(int qtd) {
        setQuantidade(qtd > 0 ? quantidade + qtd : quantidade);
    }

    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }
}
