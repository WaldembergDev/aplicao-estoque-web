package org.github.dumijdev.estoqueapp.controlador;

import org.github.dumijdev.estoqueapp.dto.NovoPedidoDTO;
import org.github.dumijdev.estoqueapp.model.Pedido;
import org.github.dumijdev.estoqueapp.service.PedidoService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.stream.IntStream;

@RequestMapping("/pedidos")
@Controller
public class PedidoControlador {
    private final PedidoService service;

    public PedidoControlador(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView listarTodos(@PageableDefault Pageable pagina) {
        var Pedidos = service.listarTodos(pagina.getPageNumber());
        var paginas = service.totalPaginas() == 0 ? new int[]{1} : IntStream.rangeClosed(1, service.totalPaginas()).toArray();

        return new ModelAndView("pages/pedidos/atendente/pedidos")
                .addObject("Pedidos", Pedidos)
                .addObject("listaVazia", Pedidos.isEmpty())
                .addObject("paginas", paginas)
                .addObject("paginaAtual", pagina.getPageNumber() < 2 ? 1 : pagina.getPageNumber() + 1);
    }

    @PostMapping
    public String salvarPedido(@ModelAttribute NovoPedidoDTO PedidoDTO) {

        System.out.println(PedidoDTO);

        service.salvar(PedidoDTO);

        return "redirect:/pedidos";
    }

    @PostMapping("/{id}")
    public String atulizarPedido(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute Pedido Pedido) {

        if (id.equals(Pedido.getId()))
            service.atualizar(Pedido);

        return "redirect:/pedidos";
    }

    @GetMapping("/{id}/deletar")
    public String deletarPedido(@PathVariable("id") Long id) {

        service.deletar(id);

        return "redirect:/Pedidos";
    }

    @GetMapping("/meus-pedidos")
    public ModelAndView meusPedidos() {
        return new ModelAndView("pages/pedidos/cliente/meus-pedidos");
    }

}
