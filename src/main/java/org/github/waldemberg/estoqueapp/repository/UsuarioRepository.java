package org.github.waldemberg.estoqueapp.repository;

import org.github.waldemberg.estoqueapp.model.Usuario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Integer> {
    @Query("select u from Usuario u order by u.nome")
    List<Usuario> buscarTodosPelaOrdemCrescente(Pageable pageable);
    @Transactional
    @Modifying
    @Query("update Usuario u set u.habilitado = ?1 where u.id = ?2")
    void desabilitar(boolean habilitado, int id);
    Optional<Usuario> findByUsername(@NonNull String username);

    boolean existsByUsername(@NonNull String username);

    default Usuario salvar(Usuario usuario) {
        if(existsByUsername(usuario.getUsername()))
            return findByUsername(usuario.getUsername()).get();
        else
            return save(usuario);
    }
}