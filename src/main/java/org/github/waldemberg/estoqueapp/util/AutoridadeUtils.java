package org.github.dumijdev.estoqueapp.util;

import org.github.dumijdev.estoqueapp.model.Autoridade;
import org.github.dumijdev.estoqueapp.model.Papel;
import org.github.dumijdev.estoqueapp.model.Usuario;
import org.github.dumijdev.estoqueapp.repository.AutoridadeRepository;
import org.github.dumijdev.estoqueapp.repository.PapelRepository;
import org.github.dumijdev.estoqueapp.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        //Pedidos
        Papel lerPedidos = papelRepository.salvar(new Papel( "ler_pedidos"));
        Papel criarPedidos = papelRepository.salvar(new Papel("criar_pedido"));
        Papel cancelarPedidos = papelRepository.salvar(new Papel("cancelar_pedido"));

        //Produtos
        Papel lerProdutos = papelRepository.salvar(new Papel("ler_produtos"));
        Papel atualizarProdutos = papelRepository.salvar(new Papel("atualizar_produto"));
        Papel deletarProduto = papelRepository.salvar(new Papel("deletar_produto"));
        Papel criarProdutos = papelRepository.salvar(new Papel("criar_produto"));


        //usuarios
        Papel lerUsuarios = papelRepository.salvar(new Papel("ler_usuarios"));
        Papel criarUsuarios = papelRepository.salvar(new Papel("criar_usuario"));
        Papel atualizarUsuarios = papelRepository.salvar(new Papel("atualizar_usuario"));

        //Setor
        Papel lerSetores = papelRepository.salvar(new Papel("ler_setores"));
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
                        deletarProduto, cancelarPedidos
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
