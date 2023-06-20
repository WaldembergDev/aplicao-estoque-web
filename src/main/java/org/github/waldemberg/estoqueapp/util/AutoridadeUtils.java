package org.github.waldemberg.estoqueapp.util;

import org.github.waldemberg.estoqueapp.model.Autoridade;
import org.github.waldemberg.estoqueapp.model.Papel;
import org.github.waldemberg.estoqueapp.model.Usuario;
import org.github.waldemberg.estoqueapp.repository.AutoridadeRepository;
import org.github.waldemberg.estoqueapp.repository.PapelRepository;
import org.github.waldemberg.estoqueapp.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.github.waldemberg.estoqueapp.util.UsuarioUtils.getUsuario;

@Component
public class AutoridadeUtils {

    private final AutoridadeRepository autoridadeRepository;
    private final PapelRepository papelRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AutoridadeUtils(AutoridadeRepository autoridadeRepository, PapelRepository papelRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.autoridadeRepository = autoridadeRepository;
        this.papelRepository = papelRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void inicializaAutoridade() {
        if (!usuarioRepository.existsByUsername("admin")) {
            //Pedidos
            Papel lerPedidos = papelRepository.salvar(new Papel("ler_pedido"));
            Papel criarPedidos = papelRepository.salvar(new Papel("criar_pedido"));
            Papel cancelarPedidos = papelRepository.salvar(new Papel("cancelar_pedido"));
            Papel atenderPedidos = papelRepository.salvar(new Papel("atender_pedido"));

            //Produtos
            Papel lerProdutos = papelRepository.salvar(new Papel("ler_produto"));
            Papel atualizarProdutos = papelRepository.salvar(new Papel("atualizar_produto"));
            Papel deletarProduto = papelRepository.salvar(new Papel("deletar_produto"));
            Papel criarProdutos = papelRepository.salvar(new Papel("criar_produto"));


            //usuarios
            Papel lerUsuarios = papelRepository.salvar(new Papel("ler_usuario"));
            Papel criarUsuarios = papelRepository.salvar(new Papel("criar_usuario"));
            Papel atualizarUsuarios = papelRepository.salvar(new Papel("atualizar_usuario"));

            //Setor
            Papel lerSetores = papelRepository.salvar(new Papel("ler_setor"));
            Papel criarSetores = papelRepository.salvar(new Papel("criar_setor"));

            //Autoridade

            //Usuario
            Autoridade usuario = autoridadeRepository.salvar(new Autoridade("USUARIO"));

            usuario.setPapeis(new ArrayList<>());

            autoridadeRepository.save(usuario);

            usuario.setPapeis(List.of(criarPedidos, cancelarPedidos));

            //Admin
            Autoridade admin = autoridadeRepository.salvar(new Autoridade("ADMIN"));

            admin.setPapeis(new ArrayList<>());

            autoridadeRepository.save(admin);

            admin.setPapeis(
                    List.of(lerPedidos, lerUsuarios, lerProdutos, lerSetores,
                            criarUsuarios, criarPedidos, criarProdutos, criarSetores,
                            atualizarUsuarios, atualizarProdutos,
                            deletarProduto, cancelarPedidos,
                            atenderPedidos
                    ));

            autoridadeRepository.save(usuario);
            autoridadeRepository.save(admin);

            //Administrador
            Usuario administrador = new Usuario();

            administrador.setAutoridade(admin);
            administrador.setUsername("admin");
            administrador.setNome("admin");
            administrador.setHabilitado(true);
            administrador.setSenha(passwordEncoder.encode("admin"));

            usuarioRepository.salvar(administrador);
        }

    }

    public static ModelAndView adicionaPapeis(ModelAndView view) {
        return view.addObject("papeis",
                Objects.requireNonNull(getUsuario())
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));
    }
}
