package com.ecommerceApi.ecommerce.services;

import com.ecommerceApi.ecommerce.exceptions.usuario.CredenciaisInvalidasException;
import com.ecommerceApi.ecommerce.mappers.UsuarioMapper;
import com.ecommerceApi.ecommerce.models.usuario.UsuarioResponseDTO;
import com.ecommerceApi.ecommerce.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    public UsuarioResponseDTO getUsuarioAutenticado() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return repository.findByEmail(email)
                .map(mapper::toResponseDTO)
                .orElseThrow(() ->
                        new CredenciaisInvalidasException(
                                "Usuário não encontrado."));
    }

}
