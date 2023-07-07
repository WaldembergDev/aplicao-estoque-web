package org.github.waldemberg.estoqueapp.model.mail;

import java.util.LinkedList;
import java.util.List;

public class EmailModel {
    private final List<String> destinatarios;
    private final String assunto;
    private Object dados;
    private final EmailType tipo;

    public EmailModel(String assunto, EmailType tipo) {
        this.destinatarios = new LinkedList<>();
        this.assunto = assunto;
        this.tipo = tipo;
    }

    public EmailModel(String assunto, Object dados, EmailType tipo) {
        this.destinatarios = new LinkedList<>();
        this.assunto = assunto;
        this.dados = dados;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo.name().toLowerCase();
    }

    public List<String> getDestinatarios() {
        return destinatarios;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setDados(Object dados) {
        this.dados = dados;
    }

    public Object getDados() {
        return dados;
    }
}
