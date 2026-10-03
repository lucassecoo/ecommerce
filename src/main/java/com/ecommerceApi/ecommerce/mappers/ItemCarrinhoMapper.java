package com.ecommerceApi.ecommerce.mappers;

import com.ecommerceApi.ecommerce.models.itemCarrinho.ItemCarrinho;
import com.ecommerceApi.ecommerce.models.itemCarrinho.ItemCarrinhoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemCarrinhoMapper {

    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "produto.nome", target = "nome")
    ItemCarrinhoResponseDTO toResponseDTO(ItemCarrinho item);

}
