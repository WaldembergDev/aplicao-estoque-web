package org.github.waldemberg.estoqueapp.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AtualizaUsuarioDTO {
    private String nome;
    private String senha;
    private String username;
    private String setor;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    @Override
    public String toString() {
        return "AtualizaUsuarioDTO{" +
                "nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", username='" + username + '\'' +
                ", setor='" + setor + '\'' +
                '}';
    }
}
