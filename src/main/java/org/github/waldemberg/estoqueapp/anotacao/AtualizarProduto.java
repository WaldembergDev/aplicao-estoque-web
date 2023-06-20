package org.github.waldemberg.estoqueapp.anotacao;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAuthority('atualizar_produto')")
public @interface AtualizarProduto {
}
