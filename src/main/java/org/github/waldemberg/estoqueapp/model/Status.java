package org.github.dumijdev.estoqueapp.model;

public enum Status {
    PENDENTE("pendente"), FINALIZADO("finalizado"), CANCELADO("cancelado");

    private final String status;

    public String getStatus() {
        return status;
    }

    Status(String status) {
        this.status = status;
    }
}
