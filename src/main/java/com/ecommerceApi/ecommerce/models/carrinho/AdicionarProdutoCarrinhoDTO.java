package com.ecommerceApi.ecommerce.models.carrinho;

public record AdicionarProdutoCarrinhoDTO(
        Long produtoId,
        Integer quantidade
) {
}
