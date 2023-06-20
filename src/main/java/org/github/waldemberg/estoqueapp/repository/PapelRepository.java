package org.github.waldemberg.estoqueapp.repository;

import org.github.waldemberg.estoqueapp.model.Papel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PapelRepository extends PagingAndSortingRepository<Papel, Integer> {
    boolean existsByNome(String nome);
    Papel findByNome(String nome);

    default Papel salvar(Papel papel) {
        if(existsByNome(papel.getNome()))
            return findByNome(papel.getNome());
        else
            return save(papel);
    }
}