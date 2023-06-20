package org.github.waldemberg.estoqueapp.util;

import org.github.waldemberg.estoqueapp.model.Setor;
import org.github.waldemberg.estoqueapp.repository.SetorRepository;
import org.springframework.stereotype.Component;

@Component
public class SetorUtils {

    private final SetorRepository repository;

    public SetorUtils(SetorRepository repository) {
        this.repository = repository;
    }

    public void inicializaSetor() {
        Setor ti = new Setor("TI");
        Setor financas = new Setor("Finanças");
        Setor rh = new Setor("RH");
        Setor compras = new Setor("Compras");

        repository.salvarTodos(ti, financas, rh, compras);
    }
}
