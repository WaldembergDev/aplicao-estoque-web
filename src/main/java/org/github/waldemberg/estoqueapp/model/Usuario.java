package org.github.dumijdev.estoqueapp.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_usuario")
    private int id;
    private String nome;
    @Column(name = "ativo")
    private boolean habilitado;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    @JsonSetter("password")
    private String senha;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Papel> papeis;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Autoridade autoridade;
    @ManyToOne
    private Setor setor;

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

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {

        var papeis = getPapeis()
                .stream()
                .map(p -> new SimpleGrantedAuthority(p.getNome()))
                .collect(Collectors.toList());

        papeis.addAll(getAutoridade().getPapeis().stream()
                .map(p -> new SimpleGrantedAuthority(p.getNome()))
                .collect(Collectors.toList()));

        return papeis;
    }

    @Override
    public String getPassword() {
        return getSenha();
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    public List<Papel> getPapeis() {
        return papeis;
    }

    public void setPapeis(List<Papel> papeis) {
        this.papeis = papeis;
    }

    public Autoridade getAutoridade() {
        return autoridade;
    }

    public void setAutoridade(Autoridade autoridade) {
        this.autoridade = autoridade;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }
}
