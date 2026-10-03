package com.ecommerceApi.ecommerce.mappers;

import com.ecommerceApi.ecommerce.models.produto.Produto;
import com.ecommerceApi.ecommerce.models.produto.ProdutoAtualizacaoDTO;
import com.ecommerceApi.ecommerce.models.produto.ProdutoCadastroDTO;
import com.ecommerceApi.ecommerce.models.produto.ProdutoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.transaction.annotation.Transactional;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imagemUrl", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    Produto toProduto(ProdutoCadastroDTO dto);

    ProdutoResponseDTO toProdutoReponseDTO(Produto produto);

    void updateProdutoFromDto(
            ProdutoAtualizacaoDTO dto,
            @MappingTarget Produto produto
    );
}


