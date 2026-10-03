package com.ecommerceApi.ecommerce.mappers;

import com.ecommerceApi.ecommerce.models.categoria.Categoria;
import com.ecommerceApi.ecommerce.models.categoria.CategoriaCadastroDto;
import com.ecommerceApi.ecommerce.models.categoria.CategoriaResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    Categoria toCategoria(CategoriaCadastroDto dto);
    CategoriaResponseDTO toCategoriaResponseDTO(Categoria dto);
}
