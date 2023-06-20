package org.github.waldemberg.estoqueapp.util;

public class ErroGeral {
    private String mensagem;

    public ErroGeral() {
    }

    public ErroGeral(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
