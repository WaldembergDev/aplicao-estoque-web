package org.github.dumijdev.estoqueapp.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_autoridade")
public class Autoridade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_autoridade")
    private int id;
    @Column(nullable = false, unique = true)
    private String nome;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Papel> papeis;

    public Autoridade() {
    }

    public Autoridade(String nome) {
        this.nome = nome;
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

    public List<Papel> getPapeis() {
        return papeis;
    }

    public void setPapeis(List<Papel> papeis) {
        this.papeis = papeis;
    }

    @Override
    public String toString() {
        return "Autoridade{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", papeis=" + papeis +
                '}';
    }
}
