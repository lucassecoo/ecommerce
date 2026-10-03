package com.ecommerceApi.ecommerce.exceptions.categoria;

public class CategoriaDesconhecidaException extends RuntimeException {
    public CategoriaDesconhecidaException(String message) {
        super(message);
    }
}
