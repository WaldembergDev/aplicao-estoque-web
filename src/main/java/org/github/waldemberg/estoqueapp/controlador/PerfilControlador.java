package org.github.dumijdev.estoqueapp.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/perfil")
@Controller
public class PerfilControlador {
    @GetMapping
    public String meuPerfil() {
        return "";
    }
}
