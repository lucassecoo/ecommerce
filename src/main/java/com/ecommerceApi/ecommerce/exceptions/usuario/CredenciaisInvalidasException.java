package com.ecommerceApi.ecommerce.exceptions.usuario;

public class CredenciaisInvalidasException extends RuntimeException{
    public CredenciaisInvalidasException(String mensagem) {
        super(mensagem);
    }
}
