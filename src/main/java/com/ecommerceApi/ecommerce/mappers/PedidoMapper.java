package com.ecommerceApi.ecommerce.mappers;

import com.ecommerceApi.ecommerce.models.pedido.Pedido;
import com.ecommerceApi.ecommerce.models.pedido.PedidoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = ItemPedidoMapper.class
)
public interface PedidoMapper {

    PedidoDTO toDTO(Pedido pedido);
}
