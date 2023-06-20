package org.github.waldemberg.estoqueapp.repository;

import org.github.waldemberg.estoqueapp.model.Setor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetorRepository extends PagingAndSortingRepository<Setor, Short> {
    Setor findByNomeContainsIgnoreCase(String nome);
    boolean existsByNomeContainsIgnoreCase(String nome);

    default void salvarTodos(Setor... setores){
        for(var setor:setores) {
            if(!existsByNomeContainsIgnoreCase(setor.getNome())){
                save(setor);
            }
        }
    }
}