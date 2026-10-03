package com.ecommerceApi.ecommerce.models.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastrarEnderecoDTO {
    @NotBlank(message = "O nome do endereço é obrigatório.")
    @Size(max = 50)
    private String nome;

    @NotBlank(message = "O CEP é obrigatório.")
    @Pattern(
            regexp = "\\d{5}-?\\d{3}",
            message = "CEP inválido."
    )
    private String cep;

    @NotBlank(message = "A rua é obrigatória.")
    @Size(max = 150)
    private String rua;

    @NotBlank(message = "O número é obrigatório.")
    @Size(max = 20)
    private String numero;

    @Size(max = 150)
    private String complemento;

    @NotBlank(message = "O bairro é obrigatório.")
    @Size(max = 100)
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória.")
    @Size(max = 100)
    private String cidade;

    @NotBlank(message = "O estado é obrigatório.")
    @Pattern(
            regexp = "^[A-Z]{2}$",
            message = "Estado deve conter a UF."
    )
    private String estado;

    private Boolean principal = false;
}
