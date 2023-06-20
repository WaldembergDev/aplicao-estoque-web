package org.github.waldemberg.estoqueapp.controlador;

import org.github.waldemberg.estoqueapp.anotacao.AtualizarProduto;
import org.github.waldemberg.estoqueapp.anotacao.DeletarProduto;
import org.github.waldemberg.estoqueapp.anotacao.LerProdutos;
import org.github.waldemberg.estoqueapp.dto.NovoProdutoDTO;
import org.github.waldemberg.estoqueapp.model.Produto;
import org.github.waldemberg.estoqueapp.service.ProdutoService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.stream.IntStream;

import static org.github.waldemberg.estoqueapp.util.AutoridadeUtils.adicionaPapeis;

@RequestMapping("/produtos")
@Controller
public class ProdutoControlador {

    private final ProdutoService service;

    public ProdutoControlador(ProdutoService service) {
        this.service = service;
    }

    @LerProdutos
    @GetMapping
    public ModelAndView listarTodos(@PageableDefault Pageable pagina,
                                    @RequestParam(name = "", required = false) String status) {
        var produtos = service.listarTodos(pagina.getPageNumber(), status);
        var paginas = service.totalPaginas(status) == 0 ? new int[]{1} : IntStream.rangeClosed(1, service.totalPaginas(status)).toArray();

        return adicionaPapeis(new ModelAndView("pages/produtos/produtos")
                .addObject("produtos", produtos)
                .addObject("listaVazia", produtos.isEmpty())
                .addObject("paginas", paginas)
                .addObject("paginaAtual", pagina.getPageNumber() < 2 ? 1 : pagina.getPageNumber() + 1));
    }

    @PostMapping
    public String salvarProduto(@ModelAttribute NovoProdutoDTO produtoDTO) {

        System.out.println(produtoDTO);

        service.salvar(produtoDTO);

        return "redirect:/produtos";
    }

    @AtualizarProduto
    @PostMapping("/{id}")
    public String atulizarProduto(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute Produto produto) {

        if (id.equals(produto.getId()))
            service.atualizar(produto);

        return "redirect:/produtos";
    }

    @DeletarProduto
    @GetMapping("/{id}/deletar")
    public String deletarProduto(@PathVariable("id") Long id) {

        service.deletar(id);

        return "redirect:/produtos";
    }

}
