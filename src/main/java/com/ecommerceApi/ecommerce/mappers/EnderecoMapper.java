package com.ecommerceApi.ecommerce.mappers;

import com.ecommerceApi.ecommerce.models.endereco.AtualizarEnderecoDTO;
import com.ecommerceApi.ecommerce.models.endereco.CadastrarEnderecoDTO;
import com.ecommerceApi.ecommerce.models.endereco.Endereco;
import com.ecommerceApi.ecommerce.models.endereco.EnderecoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Endereco toEntity(CadastrarEnderecoDTO dto);

    EnderecoDTO toDTO(Endereco endereco);

    List<EnderecoDTO> toDTOList(List<Endereco> enderecos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntityFromDto(
            AtualizarEnderecoDTO dto,
            @MappingTarget Endereco endereco
    );
}
