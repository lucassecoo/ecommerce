package com.ecommerceApi.ecommerce.controllers;

import com.ecommerceApi.ecommerce.models.categoria.CategoriaCadastroDto;
import com.ecommerceApi.ecommerce.models.categoria.CategoriaResponseDTO;
import com.ecommerceApi.ecommerce.models.categoria.CategoriaUpdateDTO;
import com.ecommerceApi.ecommerce.models.produto.ProdutoResponseDTO;
import com.ecommerceApi.ecommerce.services.CategoriaService;
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
@AllArgsConstructor
@RequestMapping("/categorias")
@Tag(name = "Categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    @Operation(
            summary = "Cadastrar categoria",
            description = "Cadastra uma nova categoria de produtos. Apenas administradores podem realizar esta operação."
    )
    @PostMapping
    public ResponseEntity<Void> cadastrarCategoria(@RequestBody CategoriaCadastroDto dto){
        categoriaService.cadastrarCategoria(dto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Listar categorias",
            description = "Retorna uma lista paginada com todas as categorias cadastradas."
    )
    @GetMapping
    public ResponseEntity<Page<CategoriaResponseDTO>> buscarTodosCategorias(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(categoriaService.buscarTodosCategorias(pageable));
    }

    @Operation(
            summary = "Buscar categoria por ID",
            description = "Retorna os dados de uma categoria específica."
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarCategoriaPorId(@PathVariable Long id){
        return ResponseEntity.ok(categoriaService.buscarCategoriaPorId(id));
    }

    @Operation(
            summary = "Atualizar categoria",
            description = "Atualiza as informações de uma categoria existente. Apenas administradores podem realizar esta operação."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarCategoria(@PathVariable Long id, @RequestBody @Valid CategoriaUpdateDTO dto){
        categoriaService.atualizarCategoria(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Excluir categoria",
            description = "Remove uma categoria do sistema. Apenas administradores podem realizar esta operação."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCategoria(@PathVariable Long id){
        categoriaService.excluirCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Listar produtos da categoria",
            description = "Retorna todos os produtos pertencentes à categoria informada."
    )
    @GetMapping("/{id}/produtos")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutosPorCategoria(@PathVariable Long id){
        return ResponseEntity.ok(categoriaService.buscarProdutosPorCategoria(id));
    }
}