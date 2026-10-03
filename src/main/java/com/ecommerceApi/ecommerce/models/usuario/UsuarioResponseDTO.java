package com.ecommerceApi.ecommerce.models.usuario;

import com.ecommerceApi.ecommerce.models.role.Role;

public record UsuarioResponseDTO(
        String email,
        String nome,
        Role role
) {

}
