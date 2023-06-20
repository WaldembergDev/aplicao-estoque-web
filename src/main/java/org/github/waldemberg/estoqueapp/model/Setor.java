package org.github.waldemberg.estoqueapp.model;

import javax.persistence.*;

@Entity
@Table(name = "tb_setor")
public class Setor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_setor")
    private short id;
    @Column(nullable = false, unique = true)
    private String nome;

    public Setor() {

    }

    public Setor(String nome) {
        this.nome = nome;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

