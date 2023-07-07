package org.github.waldemberg.estoqueapp.exceptions;

public class EstoqueInsuficienteException extends Exception {
    public EstoqueInsuficienteException() {

    }

    public EstoqueInsuficienteException(String produto) {
        super("Estoque insuficiente para o produto" + produto);
    }
}
