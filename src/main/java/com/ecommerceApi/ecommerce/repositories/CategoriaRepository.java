package com.ecommerceApi.ecommerce.repositories;

import com.ecommerceApi.ecommerce.models.categoria.Categoria;
import com.ecommerceApi.ecommerce.models.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNomeIgnoreCase(String nome);

    List<Produto> findCategoriaById(Long id);
}
