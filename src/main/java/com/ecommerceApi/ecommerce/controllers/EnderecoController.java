package com.ecommerceApi.ecommerce.controllers;

import com.ecommerceApi.ecommerce.models.endereco.AtualizarEnderecoDTO;
import com.ecommerceApi.ecommerce.models.endereco.CadastrarEnderecoDTO;
import com.ecommerceApi.ecommerce.models.endereco.EnderecoDTO;
import com.ecommerceApi.ecommerce.services.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
@Tag(name = "Endereço")
@AllArgsConstructor
public class EnderecoController {
    private final EnderecoService enderecoService;

    @Operation(
            summary = "Cadastrar um novo endereço",
            description = "Cadastra um novo endereço para o usuário autenticado."
    )
    @PostMapping()
    public ResponseEntity<EnderecoDTO> criarEndereco(@RequestBody @Valid CadastrarEnderecoDTO cadastrarEnderecoDTO){
        return ResponseEntity.ok(enderecoService.criarEndereco(cadastrarEnderecoDTO));
    }

    @Operation(
            summary = "Listar endereços",
            description = "Retorna todos os endereços cadastrados do usuário autenticado."
    )
    @GetMapping("todos")
    public ResponseEntity<Page<EnderecoDTO>> buscarTodosEnderecos(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(enderecoService.buscarTodosEnderecos(pageable));
    }

    @Operation(
            summary = "Buscar endereço por ID",
            description = "Retorna um endereço específico do usuário autenticado pelo seu identificador."
    )
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> buscarEnderecoPorId(@PathVariable Long id){
        return ResponseEntity.ok(enderecoService.buscarEnderecoPorId(id));
    }

    @Operation(
            summary = "Atualizar endereço",
            description = "Atualiza os dados de um endereço pertencente ao usuário autenticado."
    )
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@RequestBody @Valid AtualizarEnderecoDTO atualizarEnderecoDTO, @PathVariable Long id){
        return ResponseEntity.ok(enderecoService.atualizarEndereco(atualizarEnderecoDTO, id));
    }

    @Operation(
            summary = "Excluir endereço",
            description = "Remove um endereço pertencente ao usuário autenticado."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id){
        enderecoService.deletarEndereco(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Definir endereço principal",
            description = "Define um endereço do usuário autenticado como principal para futuras compras."
    )
    @PatchMapping("/{id}/principal")
    public ResponseEntity<Void> atualizarEnderecoPrincipal(@PathVariable Long id){
        enderecoService.atualizarEnderecoPrincipal(id);
        return ResponseEntity.noContent().build();
    }
}