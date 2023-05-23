package org.github.dumijdev.estoqueapp.controlador;

import org.github.dumijdev.estoqueapp.dto.NovoProdutoDTO;
import org.github.dumijdev.estoqueapp.model.Produto;
import org.github.dumijdev.estoqueapp.service.ProdutoService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.stream.IntStream;

@RequestMapping("/produtos")
@Controller
public class ProdutoControlador {

    private final ProdutoService service;

    public ProdutoControlador(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView listarTodos(@PageableDefault Pageable pagina) {
        var produtos = service.listarTodos(pagina.getPageNumber());
        var paginas = service.totalPaginas() == 0 ? new int[]{1} : IntStream.rangeClosed(1, service.totalPaginas()).toArray();

        return new ModelAndView("pages/produtos/produtos")
                .addObject("produtos", produtos)
                .addObject("listaVazia", produtos.isEmpty())
                .addObject("paginas", paginas)
                .addObject("paginaAtual", pagina.getPageNumber() < 2 ? 1 : pagina.getPageNumber() + 1);
    }

    @PostMapping
    public String salvarProduto(@ModelAttribute NovoProdutoDTO produtoDTO) {

        System.out.println(produtoDTO);

        service.salvar(produtoDTO);

        return "redirect:/produtos";
    }

    @PostMapping("/{id}")
    public String atulizarProduto(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute Produto produto) {

        if (id.equals(produto.getId()))
            service.atualizar(produto);

        return "redirect:/produtos";
    }

    @GetMapping("/{id}/deletar")
    public String deletarProduto(@PathVariable("id") Long id) {

        service.deletar(id);

        return "redirect:/produtos";
    }

}
