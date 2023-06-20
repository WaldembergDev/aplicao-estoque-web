package org.github.waldemberg.estoqueapp.controlador;

import org.github.waldemberg.estoqueapp.anotacao.DeletarUsuario;
import org.github.waldemberg.estoqueapp.anotacao.LerUsuarios;
import org.github.waldemberg.estoqueapp.dto.AtualizaUsuarioDTO;
import org.github.waldemberg.estoqueapp.dto.NovoUsuarioDTO;
import org.github.waldemberg.estoqueapp.repository.SetorRepository;
import org.github.waldemberg.estoqueapp.service.UsuarioService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.github.waldemberg.estoqueapp.util.AutoridadeUtils.adicionaPapeis;
import static org.github.waldemberg.estoqueapp.util.UsuarioUtils.getUsuario;

@Controller
public class UsuarioControlador {
    private final UsuarioService service;
    private final SetorRepository setorRepository;

    public UsuarioControlador(UsuarioService service, SetorRepository setorRepository) {
        this.service = service;
        this.setorRepository = setorRepository;
    }

    @GetMapping("/perfil")
    public ModelAndView meuPerfil() {

        var username = Objects.requireNonNull(getUsuario()).getUsername();

        var usuario = service.buscarPeloUsername(username);

        return adicionaPapeis(new ModelAndView("pages/perfil/perfil")
                .addObject("usuario", usuario));
    }

    @PostMapping("/perfil")
    public String atualizaPerfil(@ModelAttribute @Valid AtualizaUsuarioDTO usuario) {
        System.out.println(usuario);

        var username = Objects.requireNonNull(getUsuario()).getUsername();

        service.atualizar(username, usuario);

        return "redirect:/perfil";
    }

    @LerUsuarios
    @GetMapping("/usuarios")
    public ModelAndView listarUsuarios(@PageableDefault Pageable pagina) {
        var usuarios = service.listarTodos(pagina.getPageNumber());
        var paginas = service.totalPaginas() == 0 ? new int[]{1} : IntStream.rangeClosed(1, service.totalPaginas()).toArray();
        var setores = setorRepository.findAll();

        return adicionaPapeis(new ModelAndView("pages/usuarios/usuarios")
                .addObject("usuarios", usuarios)
                .addObject("setores", setores)
                .addObject("listaVazia", usuarios.isEmpty())
                .addObject("paginas", paginas)
                .addObject("paginaAtual", pagina.getPageNumber() < 2 ? 1 : pagina.getPageNumber() + 1));
    }

    @PostMapping("/usuarios")
    public String criarUsuario(@Valid @ModelAttribute NovoUsuarioDTO usuarioDTO) {

        service.salvar(usuarioDTO);

        return "redirect:/usuarios";
    }

    @PostMapping("/usuarios/{username}")
    public String atualizarUsuario(@PathVariable(name = "username") String username,
                                   @Valid @ModelAttribute AtualizaUsuarioDTO usuarioDTO) {

        service.atualizar(username, usuarioDTO);

        return "redirect:/usuarios";
    }

    @DeletarUsuario
    @GetMapping("/usuarios/{id}/deletar")
    public String deletarUsuario(@PathVariable("id") Integer id) {
        service.deletarUsuario(id);

        return "redirect:/usuarios";
    }

    @GetMapping("/{id}/status")
    public ModelAndView desabilitaUsuario(@PathVariable("id") Integer id) {

        service.desabilita(id);

        return listarUsuarios(PageRequest.of(0, 5));
    }
}
