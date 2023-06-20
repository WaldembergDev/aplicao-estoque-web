package org.github.waldemberg.estoqueapp.controlador;

import org.github.waldemberg.estoqueapp.util.AutoridadeUtils;
import org.github.waldemberg.estoqueapp.util.UsuarioUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginControlador {

    @GetMapping("/login")
    public String login() {

        if (UsuarioUtils.logado()) {
            return "redirect:/";
        }

        return "pages/login/login";
    }

    @GetMapping({"/access-denied", "/error"})
    public ModelAndView acessoNegado() {
        return AutoridadeUtils.adicionaPapeis(new ModelAndView("pages/outros/acesso-negado"));
    }

}
