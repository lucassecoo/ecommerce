package com.ecommerceApi.ecommerce.Specification;

import com.ecommerceApi.ecommerce.models.produto.Produto;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProdutoSpecification {
    public static Specification<Produto> nomeContem(String nome) {

        return (root, query, cb) ->

                nome == null || nome.isBlank()

                        ? null

                        : cb.like(
                        cb.lower(root.get("nome")),
                        "%" + nome.toLowerCase() + "%"
                );

    }

    public static Specification<Produto> categoria(Long categoriaId){

        return (root, query, cb) ->

                categoriaId == null

                        ? null

                        : cb.equal(
                        root.get("categoria").get("id"),
                        categoriaId
                );
    }

    public static Specification<Produto> precoMin(BigDecimal preco){

        return (root, query, cb) ->

                preco == null

                        ? null

                        : cb.greaterThanOrEqualTo(
                        root.get("preco"),
                        preco
                );
    }

    public static Specification<Produto> precoMax(BigDecimal preco){

        return (root, query, cb) ->

                preco == null

                        ? null

                        : cb.lessThanOrEqualTo(
                        root.get("preco"),
                        preco
                );
    }

    public static Specification<Produto> ativo(Boolean ativo){

        return (root, query, cb) ->

                ativo == null

                        ? null

                        : cb.equal(root.get("ativo"), ativo);
    }
}