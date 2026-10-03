package com.ecommerceApi.ecommerce.models.categoria;

import jakarta.validation.constraints.NotBlank;

public record CategoriaUpdateDTO(
        @NotBlank
        String nome,
        @NotBlank
        String descricao
) {
}
