package com.ecommerceApi.ecommerce.models.itemCarrinho;

import java.math.BigDecimal;

public record ItemCarrinhoResponseDTO(
        Long produtoId,
        String nome,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {
}
