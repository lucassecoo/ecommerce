package com.ecommerceApi.ecommerce.models.produto;

import jakarta.validation.constraints.Min;

public record AtualizarEstoqueDTO(

        @Min(0)
        Integer estoque

) {
}