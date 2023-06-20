package org.github.waldemberg.estoqueapp;

import org.github.waldemberg.estoqueapp.util.AutoridadeUtils;
import org.github.waldemberg.estoqueapp.util.SetorUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EstoqueApp {

    public EstoqueApp(AutoridadeUtils utils, SetorUtils setorUtils) {
        utils.inicializaAutoridade();
        setorUtils.inicializaSetor();
    }

    public static void main(String[] args) {
        SpringApplication.run(EstoqueApp.class, args);
    }
}
