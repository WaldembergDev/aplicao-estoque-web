package org.github.waldemberg.estoqueapp.dto;

public class NovoProdutoDTO {
    private String nome;
    private int quantidade;
    private int quantidadeMinima;

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

    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    @Override
    public String toString() {
        return "NovoProdutoDTO{" +
                "nome='" + nome + '\'' +
                ", quantidade=" + quantidade +
                ", quantidadeMinima=" + quantidadeMinima +
                '}';
    }
}
