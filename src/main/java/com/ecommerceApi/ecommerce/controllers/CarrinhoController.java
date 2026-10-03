package com.ecommerceApi.ecommerce.controllers;

import com.ecommerceApi.ecommerce.models.carrinho.AdicionarProdutoCarrinhoDTO;
import com.ecommerceApi.ecommerce.models.carrinho.CarrinhoResponseDTO;
import com.ecommerceApi.ecommerce.models.itemCarrinho.AtualizarQuantidadeDTO;
import com.ecommerceApi.ecommerce.services.CarrinhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
@Tag(name = "Carrinho")
@AllArgsConstructor
public class CarrinhoController {
    private final CarrinhoService carrinhoService;

    @Operation(
            summary = "Adicionar produto ao carrinho",
            description = "Adiciona um produto ao carrinho do usuário autenticado. Caso o produto já exista no carrinho, sua quantidade será atualizada."
    )
    @PostMapping
    public ResponseEntity<Void> adicionarProdutoCarrinho(AdicionarProdutoCarrinhoDTO produtoAdicionadoCarrinhoDTO) {
        carrinhoService.adicionarProdutoCarrinho(produtoAdicionadoCarrinhoDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Visualizar carrinho",
            description = "Retorna todos os itens presentes no carrinho do usuário autenticado, incluindo quantidades, subtotais e valor total."
    )
    @GetMapping
    public ResponseEntity<CarrinhoResponseDTO> listarCarrinhoUsuario() {
        return ResponseEntity.ok(carrinhoService.listarCarrinhoUsuario());
    }

    @Operation(
            summary = "Alterar quantidade de um item",
            description = "Atualiza a quantidade de um produto existente no carrinho do usuário autenticado."
    )
    @PutMapping
    public ResponseEntity<Void> alterarQuantidadeItemCarrinho(AtualizarQuantidadeDTO atualizarQuantidadeDTO) {
        carrinhoService.alterarQuantidadeItemCarrinho(atualizarQuantidadeDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Remover produto do carrinho",
            description = "Remove um produto específico do carrinho do usuário autenticado."
    )
    @DeleteMapping("produtos/{produtoId}")
    public ResponseEntity<Void> removerItem(@PathVariable Long produtoId) {
        carrinhoService.removerItem(produtoId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Limpar carrinho",
            description = "Remove todos os produtos do carrinho do usuário autenticado."
    )
    @DeleteMapping()
    public ResponseEntity<Void> limparCarrinho() {
        carrinhoService.limparCarrinho();
        return ResponseEntity.noContent().build();
    }
}