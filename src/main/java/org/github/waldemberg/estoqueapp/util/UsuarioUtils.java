package org.github.dumijdev.estoqueapp.util;

import org.github.dumijdev.estoqueapp.model.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioUtils {

    public static boolean logado() {
        return getUsuario() != null;
    }
    public static Usuario getUsuario() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Usuario)
            return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return null;
    }
}
