package org.github.dumijdev.estoqueapp.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static org.github.dumijdev.estoqueapp.util.UsuarioUtils.logado;

@Controller
public class LoginControlador {

    @GetMapping("/login")
    public String login() {

        if (logado()) {
            return "redirect:/";
        }

        return "pages/login/login";
    }

    @GetMapping("/access-denied")
    public String acessoNegado() {
        return "pages/outros/accesso-negado";
    }

}
