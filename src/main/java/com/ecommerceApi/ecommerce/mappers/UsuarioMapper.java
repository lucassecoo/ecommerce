package com.ecommerceApi.ecommerce.mappers;

import com.ecommerceApi.ecommerce.models.usuario.Usuario;
import com.ecommerceApi.ecommerce.models.usuario.UsuarioResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioResponseDTO toResponseDTO(Usuario usuario);

}