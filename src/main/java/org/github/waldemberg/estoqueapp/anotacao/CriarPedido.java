package org.github.waldemberg.estoqueapp.anotacao;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAuthority('criar_pedido')")
public @interface CriarPedido {
}
