package com.ecommerceApi.ecommerce.repositories;

import com.ecommerceApi.ecommerce.models.carrinho.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
}
