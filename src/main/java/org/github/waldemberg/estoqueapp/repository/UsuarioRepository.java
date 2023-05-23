package org.github.dumijdev.estoqueapp.repository;

import org.github.dumijdev.estoqueapp.model.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(@NonNull String username);

    boolean existsByUsername(@NonNull String username);

    default Usuario salvar(Usuario usuario) {
        if(existsByUsername(usuario.getUsername()))
            return findByUsername(usuario.getUsername()).get();
        else
            return save(usuario);
    }
}