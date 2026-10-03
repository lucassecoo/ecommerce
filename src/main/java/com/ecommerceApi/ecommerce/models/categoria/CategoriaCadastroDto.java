package com.ecommerceApi.ecommerce.models.categoria;

import jakarta.validation.constraints.NotBlank;

public record CategoriaCadastroDto(
        @NotBlank
        String nome,
        @NotBlank
        String descricao
) {

}