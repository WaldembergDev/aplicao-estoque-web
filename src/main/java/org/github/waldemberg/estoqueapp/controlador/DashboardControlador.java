package org.github.waldemberg.estoqueapp.controlador;

import org.github.waldemberg.estoqueapp.util.AutoridadeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/")
@Controller
public class DashboardControlador {

    @GetMapping
    public String redirecionaParaDashboard() {
        return "redirect:/dashboard";
    }

    @GetMapping(path = "/dashboard")
    public ModelAndView telaInicial() {
        return AutoridadeUtils.adicionaPapeis(new ModelAndView("index"));
    }
}
