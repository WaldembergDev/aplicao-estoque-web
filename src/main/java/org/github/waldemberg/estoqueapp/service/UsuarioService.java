package org.github.waldemberg.estoqueapp.service;

import org.github.waldemberg.estoqueapp.dto.AtualizaUsuarioDTO;
import org.github.waldemberg.estoqueapp.dto.NovoUsuarioDTO;
import org.github.waldemberg.estoqueapp.model.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario buscarPeloUsername(String username);
    void salvar(NovoUsuarioDTO novoUsuario);

    void deletarUsuario(Integer id);

    int totalPaginas();

    List<Usuario> listarTodos(int pagina);

    void desabilita(Integer id);

    void atualizar(String username, AtualizaUsuarioDTO usuarioDTO);
}
