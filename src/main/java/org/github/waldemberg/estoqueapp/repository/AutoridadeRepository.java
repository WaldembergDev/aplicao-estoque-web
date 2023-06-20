package org.github.waldemberg.estoqueapp.repository;

import org.github.waldemberg.estoqueapp.model.Autoridade;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoridadeRepository extends PagingAndSortingRepository<Autoridade, Integer> {
    boolean existsByNome(String nome);
    Autoridade findByNome(String nome);

    default Autoridade salvar(Autoridade autoridade){
        if(existsByNome(autoridade.getNome())) {
            System.out.println("Passei no if");
            return findByNome(autoridade.getNome());
        }
        else {
            System.out.println("Passei no else");
            return save(autoridade);
        }
    }
}