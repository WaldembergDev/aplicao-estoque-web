package org.github.waldemberg.estoqueapp.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class NovoUsuarioDTO {
    @NotNull
    @NotBlank
    @NotEmpty
    private String nome;
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 5)
    private String username;
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 6)
    private String senha;

    private String setor;
    private String email;

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
