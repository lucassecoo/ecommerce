package com.ecommerceApi.ecommerce.controllers;

import com.ecommerceApi.ecommerce.models.produto.*;
import com.ecommerceApi.ecommerce.services.ProdutoService;
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
@RequestMapping("/produtos")
@Tag(name = "Produtos")
@AllArgsConstructor
public class ProdutoController {
    private final ProdutoService produtoService;

    @Operation(
            summary = "Cadastrar produto",
            description = "Cadastra um novo produto no catálogo. Apenas administradores podem realizar esta operação."
    )
    @PostMapping()
    public ResponseEntity<Void> cadastrarProduto(@RequestBody @Valid ProdutoCadastroDTO dto){
        produtoService.cadastrarProduto(dto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna os detalhes de um produto específico pelo seu identificador."
    )
    @GetMapping("{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.buscarProdutoPorId(id));
    }

    @Operation(
            summary = "Listar produtos",
            description = "Retorna uma lista paginada de produtos, permitindo filtros por nome, categoria, faixa de preço e ordenação."
    )
    @GetMapping()
    public ResponseEntity<Page<ProdutoResponseDTO>> filtrar(

            @ParameterObject ProdutoFiltroDTO filtro,

            @ParameterObject Pageable pageable
    ){

        return ResponseEntity.ok(

                produtoService.filtrarProdutos(
                        filtro,
                        pageable
                )

        );
    }

    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza as informações de um produto existente. Apenas administradores podem realizar esta operação."
    )
    @PutMapping("{id}")
    public ResponseEntity<Void> atualizarProduto(@PathVariable Long id, @RequestBody @Valid ProdutoAtualizacaoDTO dto){
        produtoService.atualizarProduto(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Desativar produto",
            description = "Desativa um produto, impedindo que ele seja exibido para os clientes sem removê-lo do banco de dados."
    )
    @PutMapping("desativar/{id}")
    public ResponseEntity<Void> desativarProduto(@PathVariable Long id){
        produtoService.desativarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Ativar produto",
            description = "Ativa um produto, permitindo que ele seja exibido para os clientes."
    )
    @PutMapping("ativar/{id}")
    public ResponseEntity<Void> ativarProduto(@PathVariable Long id){
        produtoService.ativarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Adicionar estoque",
            description = "Adiciona o produto no estoque."
    )
    @PutMapping("/estoque")
    public ResponseEntity<Void> adicionarEstoqueProduto(@RequestParam Long produtoId, @RequestParam Integer quantidade) {
        produtoService.adicionarEstoqueProduto(produtoId, quantidade);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Listar produtos recentes",
            description = "Retorna os produtos adicionados mais recentemente ao catálogo."
    )
    @GetMapping("/recentes")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarRecentes() {

        return ResponseEntity.ok(
                produtoService.buscarProdutosRecentes()
        );
    }

    @Operation(
            summary = "Listar produtos relacionados",
            description = "Retorna produtos da mesma categoria do produto informado para sugestões na página de detalhes."
    )
    @GetMapping("/{id}/relacionados")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarRelacionados(

            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                produtoService.buscarProdutosRelacionados(id)
        );
    }

    @Operation(
            summary = "Atualizar estoque",
            description = "Atualiza a quantidade do produto no estoque."
    )
    @PatchMapping("/{id}/estoque")
    public ResponseEntity<Void> atualizarEstoque(

            @PathVariable Long id,

            @RequestBody @Valid AtualizarEstoqueDTO dto
    ) {

        produtoService.atualizarEstoque(id, dto.estoque());

        return ResponseEntity.noContent().build();
    }
}