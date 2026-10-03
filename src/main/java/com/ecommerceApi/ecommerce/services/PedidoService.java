package com.ecommerceApi.ecommerce.services;

import com.ecommerceApi.ecommerce.exceptions.pedidos.CarrinhoVazioException;
import com.ecommerceApi.ecommerce.exceptions.pedidos.ImpossivelAlterarStatusPedidoException;
import com.ecommerceApi.ecommerce.exceptions.pedidos.PedidoNaoEncontradoException;
import com.ecommerceApi.ecommerce.mappers.ItemPedidoMapper;
import com.ecommerceApi.ecommerce.mappers.PedidoMapper;
import com.ecommerceApi.ecommerce.models.carrinho.Carrinho;
import com.ecommerceApi.ecommerce.models.endereco.Endereco;
import com.ecommerceApi.ecommerce.models.itemCarrinho.ItemCarrinho;
import com.ecommerceApi.ecommerce.models.itemPedido.ItemPedido;
import com.ecommerceApi.ecommerce.models.pedido.Pedido;
import com.ecommerceApi.ecommerce.models.pedido.PedidoDTO;
import com.ecommerceApi.ecommerce.models.statusPedido.StatusPedido;
import com.ecommerceApi.ecommerce.models.usuario.Usuario;
import com.ecommerceApi.ecommerce.repositories.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.ecommerceApi.ecommerce.models.statusPedido.StatusPedido.*;

@Service
@AllArgsConstructor
public class PedidoService {
    private final CarrinhoService carrinhoService;
    private final ProdutoService produtoService;
    private final ItemPedidoMapper itemPedidoMapper;
    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final AuthService authService;
    private final EnderecoService enderecoService;

    private static final Map<StatusPedido, Set<StatusPedido>> TRANSICOES = Map.of(
            AGUARDANDO_PAGAMENTO, Set.of(PAGO, CANCELADO),
            PAGO, Set.of(PROCESSANDO, CANCELADO),
            PROCESSANDO, Set.of(ENVIADO, CANCELADO),
            ENVIADO, Set.of(ENTREGUE),
            ENTREGUE, Set.of(),
            CANCELADO, Set.of()
    );

    @Transactional
    public PedidoDTO finalizarPedido(Long enderecoId) {
        Carrinho carrinhoUsuario = carrinhoService.obterCarrinhoUsuario();
        validarCarrinhoVazio(carrinhoUsuario);
        validarEstoqueProdutosDoCarrinho(carrinhoUsuario);
        validarProdutosAtivos(carrinhoUsuario);
        Pedido pedido = criarPedido(carrinhoUsuario);
        Endereco endereco =
                enderecoService.buscarEnderecoEntityPorId(enderecoId);
        pedido.setEnderecoEntrega(endereco);
        pedidoRepository.save(pedido);
        pedido.getItens().forEach(item ->
                produtoService.removerEstoqueProduto(
                        item.getProduto(),
                        item.getQuantidade()
                )
        );
        carrinhoService.limparCarrinho();
        return pedidoMapper.toDTO(pedido);
    }

    @Transactional
    public void atualizarStatusPedido(Long pedidoId, StatusPedido statusPedido){
        Pedido pedido = buscarPedido(pedidoId);
        validarAlteracoesPedido(pedido, statusPedido);
        pedido.setStatus(statusPedido);
    }

    @Transactional
    public void cancelarPedido(Long pedidoId) {
        Pedido pedido = buscarPedido(pedidoId);

        validarAlteracoesPedido(pedido, CANCELADO);
        pedido.getItens().forEach(item ->
                produtoService.adicionarEstoqueProduto(
                        item.getProduto().getId(),
                        item.getQuantidade()
                )
        );
        pedido.setStatus(CANCELADO);
    }

    @Transactional(readOnly = true)
    public Page<PedidoDTO> buscarPedidosDoUsuario(Pageable pageable) {
        Usuario usuario = authService.getUsuarioAutenticado();

        return pedidoRepository
                .findByUsuarioId(usuario.getId(), pageable)
                .map(pedidoMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public PedidoDTO buscarPedidosPorId(Long pedidoId){
        Usuario usuario = authService.getUsuarioAutenticado();

        Pedido pedido = usuario.getPedidos()
                .stream()
                .filter(p -> p.getId().equals(pedidoId))
                .findFirst()
                .orElseThrow(() -> new PedidoNaoEncontradoException(
                        "Pedido não encontrado."
                ));

        return pedidoMapper.toDTO(pedido);
    }

    @Transactional(readOnly = true)
    public Page<PedidoDTO> buscarTodosPedidos(Pageable pageable) {

        return pedidoRepository.findAll(pageable)
                .map(pedidoMapper::toDTO);
    }

    private Pedido buscarPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(
                        "Pedido não encontrado."
                ));
    }

    private Pedido criarPedido(Carrinho carrinho) {
        validarCarrinhoVazio(carrinho);

        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        List<ItemPedido> itensPedido =
                itemPedidoMapper.toItemPedidoList(carrinho.getItens());

        itensPedido.forEach(item -> item.setPedido(pedido));

        pedido.setItens(itensPedido);

        Usuario usuario = authService.getUsuarioAutenticado();
        pedido.setUsuario(usuario);

        pedido.setValorTotal(carrinho.getValorTotal());
        return pedido;
    }

    private void validarAlteracoesPedido(Pedido pedido, StatusPedido novoStatus){
        StatusPedido statusAtual = pedido.getStatus();
        Set<StatusPedido> proximos =
                TRANSICOES.get(statusAtual);

        if (proximos == null) {
            throw new IllegalStateException(
                    "Status não configurado.");
        }
        if (!TRANSICOES.get(statusAtual).contains(novoStatus)) {
            throw new ImpossivelAlterarStatusPedidoException(
                    "Transição de status inválida.");
        }
    }

    private void validarEstoqueProdutosDoCarrinho(Carrinho carrinho) {
        List<ItemCarrinho> itens = carrinho.getItens();
        for (ItemCarrinho item : itens) {
            produtoService.validarQuantidadeProduto(item.getProduto().getId(), item.getQuantidade());
        }
    }

    private void validarProdutosAtivos(Carrinho carrinho) {
        List<ItemCarrinho> itens = carrinho.getItens();
        for (ItemCarrinho item : itens) {
            produtoService.validarProdutoAtivo(item.getProduto().getId());
        }
    }

    private void validarCarrinhoVazio(Carrinho carrinhoUsuario){
        if (carrinhoUsuario.getItens().isEmpty()) {
            throw new CarrinhoVazioException("Carrinho esta vazio, impossível finalizar pedido.");
        }
    }
}
