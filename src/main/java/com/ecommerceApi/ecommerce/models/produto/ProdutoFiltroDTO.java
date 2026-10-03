package com.ecommerceApi.ecommerce.models.produto;

import java.math.BigDecimal;

public record ProdutoFiltroDTO(

        String nome,

        Long categoriaId,

        BigDecimal precoMin,

        BigDecimal precoMax,

        Boolean ativo

) {
}