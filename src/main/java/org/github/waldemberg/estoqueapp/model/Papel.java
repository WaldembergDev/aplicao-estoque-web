package org.github.dumijdev.estoqueapp.model;

import javax.persistence.*;

@Entity
@Table(name = "tb_papel")
public class Papel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_papel")
    private int id;
    @Column(nullable = false, unique = true)
    private String nome;

    public Papel(String nome) {
        this.nome = nome;
    }

    public Papel() {
    }

    @Override
    public String toString() {
        return "Papel{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
