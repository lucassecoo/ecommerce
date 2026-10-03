package com.ecommerceApi.ecommerce.exceptions.categoria;

public class CategoriaJaExistenteException extends RuntimeException {
    public CategoriaJaExistenteException(String message) {
        super(message);
    }
}
