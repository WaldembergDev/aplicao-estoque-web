package org.github.waldemberg.estoqueapp.util;

import org.github.waldemberg.estoqueapp.model.Usuario;
import org.github.waldemberg.estoqueapp.repository.UsuarioRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Component
@Order(-100)
public class UsuarioUtils {

    private static UsuarioRepository repository;

    public UsuarioUtils(UsuarioRepository usuarioRepository) {
        repository = usuarioRepository;
    }

    public static boolean logado() {
        return getUsuario() != null;
    }

    public static Usuario getUsuario() {
        if (getContext().getAuthentication().getPrincipal() instanceof Usuario) {
            var username = ((Usuario) getContext().getAuthentication().getPrincipal()).getUsername();
            return repository.findByUsername(username).orElseThrow();
        }
        return null;
    }
}
