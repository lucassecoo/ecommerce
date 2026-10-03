package com.ecommerceApi.ecommerce.models.produto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoCadastroDTO (
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        String descricao,
        @NotNull
        @DecimalMin("0.01")
        BigDecimal preco,
        @NotNull
        @Min(0)
        Integer estoque,
        @NotNull
        Long categoriaId){

}
