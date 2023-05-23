package org.github.dumijdev.estoqueapp;

import org.github.dumijdev.estoqueapp.util.AutoridadeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EstoqueApp {

    public EstoqueApp(AutoridadeUtils utils) {
        utils.inicializaAutoridade();
    }

    public static void main(String[] args) {
        SpringApplication.run(EstoqueApp.class, args);
    }
}
