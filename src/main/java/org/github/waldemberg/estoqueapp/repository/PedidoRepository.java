package org.github.waldemberg.estoqueapp.repository;

import org.github.waldemberg.estoqueapp.model.Pedido;
import org.github.waldemberg.estoqueapp.model.Status;
import org.github.waldemberg.estoqueapp.model.Usuario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PedidoRepository extends PagingAndSortingRepository<Pedido, Long> {
    List<Pedido> findByRequerente(Usuario requerente, Pageable pagina);
    List<Pedido> findByStatusIn(@NonNull Collection<Status> statuses, Pageable pagina);

}
