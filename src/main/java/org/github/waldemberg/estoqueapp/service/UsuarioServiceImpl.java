package org.github.waldemberg.estoqueapp.service;

import org.github.waldemberg.estoqueapp.dto.AtualizaUsuarioDTO;
import org.github.waldemberg.estoqueapp.dto.NovoUsuarioDTO;
import org.github.waldemberg.estoqueapp.model.mail.EmailModel;
import org.github.waldemberg.estoqueapp.model.mail.EmailType;
import org.github.waldemberg.estoqueapp.model.Pedido;
import org.github.waldemberg.estoqueapp.model.Usuario;
import org.github.waldemberg.estoqueapp.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final SetorRepository setorRepository;
    private final AutoridadeRepository autoridadeRepository;
    private final PedidoRepository pedidoRepository;
    private final PapelRepository papelRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public UsuarioServiceImpl(UsuarioRepository repository, SetorRepository repository1, AutoridadeRepository autoridadeRepository, PedidoRepository pedidoRepository, PapelRepository papelRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.repository = repository;
        this.setorRepository = repository1;
        this.autoridadeRepository = autoridadeRepository;
        this.pedidoRepository = pedidoRepository;
        this.papelRepository = papelRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public Usuario buscarPeloUsername(String username) {
        return repository.findByUsername(username).orElseThrow();
    }

    @Override
    public void salvar(NovoUsuarioDTO novoUsuario) {
        var usuario = new Usuario();

        usuario.setNome(novoUsuario.getNome());
        usuario.setUsername(novoUsuario.getUsername());
        usuario.setEmail(novoUsuario.getEmail());
        usuario.setAutoridade(autoridadeRepository.findByNome("USUARIO"));

        if (setorRepository.existsByNomeContainsIgnoreCase(novoUsuario.getSetor())) {
            usuario.setSetor(setorRepository.findByNomeContainsIgnoreCase(novoUsuario.getSetor()));

            if (novoUsuario.getSetor().equals("Compras")) {
                var papel1 = papelRepository.findByNome("ler_pedido");
                var papel2 = papelRepository.findByNome("atender_pedido");
                var papel3 = papelRepository.findByNome("ler_produto");
                var papel4 = papelRepository.findByNome("criar_produto");
                var papel5 = papelRepository.findByNome("atualizar_produto");

                usuario.setPapeis(List.of(papel1, papel2, papel3, papel4, papel5));
            }
        }

        usuario.setSenha(passwordEncoder.encode(novoUsuario.getSenha()));

        repository.salvar(usuario);

        EmailModel emailModel = new EmailModel("DADOS DE ACESSO PARA " + usuario.getNome(), novoUsuario, EmailType.CADASTRO);

        emailModel.getDestinatarios().add(usuario.getEmail());

        emailService.enviarEmail(emailModel);
    }

    @Override
    public void deletarUsuario(Integer id) {
        repository.findById(id).ifPresent(usuario -> {
            var pedidos = pedidoRepository.findByRequerente(usuario, PageRequest.of(0, 99999))
                    .stream()
                    .map(Pedido::getId)
                    .collect(Collectors.toList());

            pedidoRepository.deleteAllById(pedidos);

            repository.deleteById(id);
        });
    }

    @Override
    public int totalPaginas() {
        return repository.findAll(PageRequest.of(0, 5)).getTotalPages();
    }

    @Override
    public List<Usuario> listarTodos(int page) {
        var pagina = PageRequest.of(page > 1 ? page - 1 : 0, 5);
        return repository.buscarTodosPelaOrdemCrescente(pagina);
    }

    @Override
    public void desabilita(Integer id) {
        repository.findById(id).ifPresent(u -> repository.desabilitar(!u.isHabilitado(), id));
    }

    @Override
    public void atualizar(String username, AtualizaUsuarioDTO usuarioDTO) {
        repository.findByUsername(username)
                .ifPresent(usuario -> {
                    if (usuarioDTO.getNome() != null) {
                        usuario.setNome(usuarioDTO.getNome());
                    }

                    if (usuarioDTO.getSenha() != null) {
                        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
                    }

                    if (usuarioDTO.getSetor() != null) {
                        if (!"".equals(usuarioDTO.getSetor())) {
                            var setor = setorRepository.findByNomeContainsIgnoreCase(usuarioDTO.getSetor());
                            usuario.setSetor(setor);
                        } else if (!"SEM SETOR".equalsIgnoreCase(usuarioDTO.getSetor())) {
                            usuario.setSetor(null);
                        }
                    }

                    repository.save(usuario);

                });

    }
}
