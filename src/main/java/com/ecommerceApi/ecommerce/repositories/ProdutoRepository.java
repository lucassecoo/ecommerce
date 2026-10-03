package com.ecommerceApi.ecommerce.repositories;

import com.ecommerceApi.ecommerce.models.produto.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {
    Page<Produto> findByNomeContainingIgnoreCaseAndAtivoTrue(
            String nome,
            Pageable pageable
    );

    Page<Produto> findByCategoriaIdAndAtivoTrue(
            Long categoriaId,
            Pageable pageable
    );

    List<Produto> findTop10ByAtivoTrueOrderByCriadoEmDesc();

    List<Produto> findTop8ByCategoriaIdAndIdNotAndAtivoTrue(
            Long categoriaId,
            Long produtoId
    );
}
