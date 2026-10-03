package com.ecommerceApi.ecommerce.models.carrinho;

import com.ecommerceApi.ecommerce.models.itemCarrinho.ItemCarrinhoResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public record CarrinhoResponseDTO(
        Long id,
        List<ItemCarrinhoResponseDTO> itens,
        BigDecimal total
) {
}
