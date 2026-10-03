package com.ecommerceApi.ecommerce.mappers;

import com.ecommerceApi.ecommerce.models.itemCarrinho.ItemCarrinho;
import com.ecommerceApi.ecommerce.models.itemPedido.ItemPedido;
import com.ecommerceApi.ecommerce.models.itemPedido.ItemPedidoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    ItemPedido toItemPedido(ItemCarrinho itemCarrinho);

    List<ItemPedido> toItemPedidoList(List<ItemCarrinho> itensCarrinho);

    @Mapping(target = "produtoId", source = "produto.id")
    @Mapping(target = "nomeProduto", source = "produto.nome")
    ItemPedidoDTO toDTO(ItemPedido item);

    List<ItemPedidoDTO> toDTOList(List<ItemPedido> itens);
}
