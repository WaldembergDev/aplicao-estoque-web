package org.github.waldemberg.estoqueapp.controlador;

import org.github.waldemberg.estoqueapp.anotacao.CriarPedido;
import org.github.waldemberg.estoqueapp.anotacao.LerPedidos;
import org.github.waldemberg.estoqueapp.dto.NovoPedidoDTO;
import org.github.waldemberg.estoqueapp.exceptions.EstoqueInsuficienteException;
import org.github.waldemberg.estoqueapp.model.Pedido;
import org.github.waldemberg.estoqueapp.repository.ProdutoRepository;
import org.github.waldemberg.estoqueapp.service.PedidoService;
import org.github.waldemberg.estoqueapp.util.ErroGeral;
import org.github.waldemberg.estoqueapp.util.EstoqueAbaixo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.github.waldemberg.estoqueapp.util.AutoridadeUtils.adicionaPapeis;
import static org.github.waldemberg.estoqueapp.util.UsuarioUtils.getUsuario;

@RequestMapping("/pedidos")
@Controller
public class PedidoControlador {
    private final PedidoService service;
    private final ProdutoRepository repository;

    public PedidoControlador(PedidoService service, ProdutoRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @LerPedidos
    @GetMapping
    public ModelAndView listarTodos(@PageableDefault Pageable pagina,
                                    @RequestParam(name = "status", required = false) String status) {
        var pedidos = service.listarTodos(pagina.getPageNumber(), status);
        var paginas = service.totalPaginas() == 0 ? new int[]{1} : IntStream.rangeClosed(1, service.totalPaginas()).toArray();
        var produtos = repository.buscarProdutosComEstoqueMaiorQue(0);

        return adicionaPapeis(new ModelAndView("pages/pedidos/atendente/pedidos")
                .addObject("pedidos",pedidos)
                .addObject("listaVazia", pedidos.isEmpty())
                .addObject("paginas", paginas)
                .addObject("paginaAtual", pagina.getPageNumber() < 2 ? 1 : pagina.getPageNumber() + 1)
                .addObject("produtos", produtos));
    }

    @CriarPedido
    @PostMapping
    public ModelAndView salvarPedido(@ModelAttribute NovoPedidoDTO pedido) {

        var erros = service.salvar(pedido);

        if(erros.isEmpty())
            return listarTodos(PageRequest.of(0, 5), null);

        var mensagens = erros.stream().map(EstoqueAbaixo::getMensagem).collect(Collectors.joining(", "));

        return listarTodos(PageRequest.of(0, 5), null)
                .addObject("erro", new ErroGeral(mensagens));
    }

    @PostMapping("/{id}")
    public String atualizarPedido(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute Pedido pedido) {

        if (id.equals(pedido.getId()))
            service.atualizar(pedido);

        return "redirect:/pedidos";
    }

    @GetMapping("/{id}/cancelar")
    public String negarPedido(@PathVariable("id") Long id) {

        service.cancelar(id);

        if (Objects.requireNonNull(getUsuario())
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equalsIgnoreCase("ler_pedido")))
            return "redirect:/pedidos";

        return "redirect:/pedidos/meus-pedidos";
    }

    @GetMapping("/{id}/aceitar")
    public String aceitarPedido(@PathVariable("id") Long id) {

        try {
            service.aceitar(id);
        } catch (EstoqueInsuficienteException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/pedidos";
    }


    @GetMapping("/meus-pedidos")
    public ModelAndView meusPedidos(Pageable pagina,
                                    @RequestParam(name = "status", required = false) String status) {
        List<Pedido> meusPedidos = service.buscarMeusPedidos(pagina.getPageNumber(), status);

        var paginas = meusPedidos.size() / 5 == 0 ? new int[]{1} : IntStream.rangeClosed(1, meusPedidos.size() / 5).toArray();
        var produtos = repository.buscarProdutosComEstoqueMaiorQue(0);

        return adicionaPapeis(new ModelAndView("pages/pedidos/cliente/meus-pedidos")
                .addObject("pedidos", meusPedidos)
                .addObject("listaVazia", meusPedidos.isEmpty())
                .addObject("paginas", paginas)
                .addObject("paginaAtual", pagina.getPageNumber() < 2 ? 1 : pagina.getPageNumber() + 1)
                .addObject("produtos", produtos));
    }
    @PostMapping("/meus-pedidos")
    public ModelAndView salvarMeuPedido(NovoPedidoDTO pedido){
        var erros = service.salvar(pedido);
        var p = PageRequest.of(0, 5);

        if(erros.isEmpty())
            return meusPedidos(p, null);

        var mensagens = erros.stream().map(EstoqueAbaixo::getMensagem).collect(Collectors.joining(", "));

        return meusPedidos(p, null);
    }

}
