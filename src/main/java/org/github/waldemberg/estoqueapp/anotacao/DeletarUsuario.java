package org.github.waldemberg.estoqueapp.anotacao;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAuthority('deletar_usuario')")
public @interface DeletarUsuario {
}
