package org.github.dumijdev.estoqueapp.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class DashboardControlador {

    @GetMapping
    public String redirecionaParaDashboard() {
        return "redirect:/dashboard";
    }

    @GetMapping(path = "/dashboard")
    public String telaInicial() {
        return "index";
    }
}
