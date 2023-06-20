package org.github.waldemberg.estoqueapp.configuration.service;

import org.github.waldemberg.estoqueapp.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository repo;

    public CustomUserDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return user;
    }
}
