package com.ecommerceApi.ecommerce.mappers;

import com.ecommerceApi.ecommerce.models.carrinho.Carrinho;
import com.ecommerceApi.ecommerce.models.carrinho.CarrinhoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = ItemCarrinhoMapper.class
)
public interface CarrinhoMapper {

    @Mapping(source = "valorTotal", target = "total")
    CarrinhoResponseDTO carrinhoToResponseDto(Carrinho carrinho);
}
