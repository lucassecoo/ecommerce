package com.ecommerceApi.ecommerce.services;

import com.ecommerceApi.ecommerce.models.usuario.Usuario;
import com.ecommerceApi.ecommerce.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(
            String email) throws UsernameNotFoundException {

        Usuario usuario =
                repository.getUsuarioByEmail(email);

        if(usuario == null){
            throw new UsernameNotFoundException(
                    "Usuário não encontrado"
            );
        }

        return usuario;
    }
}