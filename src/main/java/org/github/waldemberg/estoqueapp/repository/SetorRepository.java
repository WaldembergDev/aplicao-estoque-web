package org.github.dumijdev.estoqueapp.repository;

import org.github.dumijdev.estoqueapp.model.Setor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetorRepository extends PagingAndSortingRepository<Setor, Short> {
}