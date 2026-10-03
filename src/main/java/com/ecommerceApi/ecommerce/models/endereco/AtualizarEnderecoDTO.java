package com.ecommerceApi.ecommerce.models.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarEnderecoDTO {

    @NotBlank
    @Size(max = 50)
    private String nome;

    @NotBlank
    @Pattern(regexp = "\\d{5}-?\\d{3}")
    private String cep;

    @NotBlank
    @Size(max = 150)
    private String rua;

    @NotBlank
    @Size(max = 20)
    private String numero;

    @Size(max = 150)
    private String complemento;

    @NotBlank
    @Size(max = 100)
    private String bairro;

    @NotBlank
    @Size(max = 100)
    private String cidade;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{2}$")
    private String estado;

    private Boolean principal;
}