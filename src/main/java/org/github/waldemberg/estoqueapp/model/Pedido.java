package org.github.waldemberg.estoqueapp.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gn_pedido")
    private long id;
    private boolean visto = false;
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDENTE;
    private LocalDateTime dataInicio = LocalDateTime.now();
    private LocalDateTime dataFim;
    private int numeroPedido;
    @ManyToOne
    @JoinColumn
    private Usuario atendente;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario requerente;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;

    public Pedido() {
        itens = new ArrayList<>();
    }

    public String getDataInicioFormatada() {
        return dataInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy, hh:mm"));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isVisto() {
        return visto;
    }

    public boolean getVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public Usuario getAtendente() {
        return atendente;
    }

    public void setAtendente(Usuario atendente) {
        this.atendente = atendente;
    }

    public Usuario getRequerente() {
        return requerente;
    }

    public void setRequerente(Usuario requerente) {
        this.requerente = requerente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }
}
