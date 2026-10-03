package com.ecommerceApi.ecommerce.models.pedido;

import com.ecommerceApi.ecommerce.models.itemPedido.ItemPedidoDTO;
import com.ecommerceApi.ecommerce.models.statusPedido.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoDTO {

    private Long id;
    private LocalDateTime dataPedido;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private List<ItemPedidoDTO> itens;
}
