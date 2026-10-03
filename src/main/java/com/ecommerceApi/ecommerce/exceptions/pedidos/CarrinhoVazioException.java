package com.ecommerceApi.ecommerce.exceptions.pedidos;

public class CarrinhoVazioException extends RuntimeException {
    public CarrinhoVazioException(String message) {
        super(message);
    }
}
