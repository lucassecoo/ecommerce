package com.ecommerceApi.ecommerce.models.itemCarrinho;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarQuantidadeDTO{
    private Integer quantidade;
    private Long itemId;
}
