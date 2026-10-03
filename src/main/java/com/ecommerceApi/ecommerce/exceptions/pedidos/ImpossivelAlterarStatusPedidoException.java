package com.ecommerceApi.ecommerce.exceptions.pedidos;

public class ImpossivelAlterarStatusPedidoException extends RuntimeException {
    public ImpossivelAlterarStatusPedidoException(String message) {
        super(message);
    }
}
