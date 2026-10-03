package com.ecommerceApi.ecommerce.controllers;

import com.ecommerceApi.ecommerce.models.pedido.PedidoDTO;
import com.ecommerceApi.ecommerce.models.statusPedido.StatusPedido;
import com.ecommerceApi.ecommerce.services.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos")
@AllArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

    @Operation(
            summary = "Finalizar pedido",
            description = "Cria um novo pedido utilizando os produtos presentes no carrinho do usuário autenticado e o endereço de entrega informado. Após a conclusão, o estoque é atualizado e o carrinho é esvaziado."
    )
    @PostMapping("/finalizar/{enderecoId}")
    public ResponseEntity<PedidoDTO> finalizarPedido(@PathVariable Long enderecoId) {
        return ResponseEntity.ok(pedidoService.finalizarPedido(enderecoId));
    }

    @Operation(
            summary = "Listar meus pedidos",
            description = "Retorna uma lista paginada com todos os pedidos realizados pelo usuário autenticado."
    )
    @GetMapping("usuario")
    public ResponseEntity<Page<PedidoDTO>> buscarPedidosDoUsuario(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(pedidoService.buscarPedidosDoUsuario(pageable));
    }

    @Operation(
            summary = "Buscar pedido por ID",
            description = "Retorna os detalhes de um pedido específico pertencente ao usuário autenticado."
    )
    @GetMapping("/{pedidoId}")
    public ResponseEntity<PedidoDTO> buscarPedidosPorId(@PathVariable Long pedidoId){
        return ResponseEntity.ok(pedidoService.buscarPedidosPorId(pedidoId));
    }

    @Operation(
            summary = "Listar todos os pedidos",
            description = "Retorna uma lista paginada com todos os pedidos cadastrados no sistema. Apenas administradores devem possuir acesso a este endpoint."
    )
    @GetMapping
    public ResponseEntity<Page<PedidoDTO>> buscarTodosPedidos(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(pedidoService.buscarTodosPedidos(pageable));
    }

    @Operation(
            summary = "Atualizar status do pedido",
            description = "Atualiza o status de um pedido seguindo as transições permitidas pelo fluxo da aplicação. Apenas administradores podem realizar esta operação."
    )
    @PutMapping("/atualizar-status/{pedidoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> atualizarStatusPedido(
            @PathVariable Long pedidoId,
            @RequestParam StatusPedido statusPedido
    ){
        pedidoService.atualizarStatusPedido(pedidoId, statusPedido);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Cancelar pedido",
            description = "Cancela um pedido quando permitido pelo fluxo de status e realiza a devolução dos produtos ao estoque."
    )
    @PutMapping("/cancelar/{pedidoId}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long pedidoId) {
        pedidoService.cancelarPedido(pedidoId);
        return ResponseEntity.noContent().build();
    }

}