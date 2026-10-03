package com.ecommerceApi.ecommerce.services;

import com.ecommerceApi.ecommerce.exceptions.produto.ProdutoNaoEncontradoException;
import com.ecommerceApi.ecommerce.exceptions.produto.QuantidadeInvalidaException;
import com.ecommerceApi.ecommerce.mappers.CarrinhoMapper;
import com.ecommerceApi.ecommerce.models.carrinho.AdicionarProdutoCarrinhoDTO;
import com.ecommerceApi.ecommerce.models.carrinho.Carrinho;
import com.ecommerceApi.ecommerce.models.carrinho.CarrinhoResponseDTO;
import com.ecommerceApi.ecommerce.models.itemCarrinho.AtualizarQuantidadeDTO;
import com.ecommerceApi.ecommerce.models.itemCarrinho.ItemCarrinho;
import com.ecommerceApi.ecommerce.models.produto.Produto;
import com.ecommerceApi.ecommerce.models.usuario.Usuario;
import com.ecommerceApi.ecommerce.repositories.CarrinhoRepository;
import com.ecommerceApi.ecommerce.repositories.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarrinhoService {
    private final CarrinhoRepository carrinhoRepository;
    private final AuthService authService;
    private final ProdutoService produtoService;
    private final CarrinhoMapper carrinhoMapper;
    private final ProdutoRepository produtoRepository;

    public Carrinho obterCarrinhoUsuario() {
        Usuario usuario = authService.getUsuarioAutenticado();

        if (usuario.getCarrinho() == null) {
            Carrinho carrinho = new Carrinho();
            carrinho.setUsuario(usuario);
            carrinho.setValorTotal(BigDecimal.ZERO);

            usuario.setCarrinho(carrinho);

            return carrinhoRepository.save(carrinho);
        }

        return usuario.getCarrinho();
    }

    public void adicionarProdutoCarrinho(AdicionarProdutoCarrinhoDTO produtoAdicionadoCarrinhoDTO) {
        if (produtoAdicionadoCarrinhoDTO.quantidade() <= 0) {
            throw new QuantidadeInvalidaException("Quantidade deve ser maior que 0!");
        }

        Produto produto = produtoRepository.findById(produtoAdicionadoCarrinhoDTO.produtoId())
                .orElseThrow(() ->
                        new ProdutoNaoEncontradoException(
                                "Produto não encontrado"));

        Carrinho carrinho = obterCarrinhoUsuario();

        Optional<ItemCarrinho> itemExistente =
                buscarItemCarrinho(carrinho, produtoAdicionadoCarrinhoDTO.produtoId());
        int quantidadeFinal = itemExistente
                .map(i -> i.getQuantidade() + produtoAdicionadoCarrinhoDTO.quantidade())
                .orElse(produtoAdicionadoCarrinhoDTO.quantidade());
        produtoService.validarQuantidadeProduto(produtoAdicionadoCarrinhoDTO.produtoId(), quantidadeFinal);
        if (itemExistente.isPresent()) {
            ItemCarrinho item = itemExistente.get();
            item.adicionarQuantidade(produtoAdicionadoCarrinhoDTO.quantidade());
        } else {
            ItemCarrinho novoItem = new ItemCarrinho();
            novoItem.setCarrinho(carrinho);
            novoItem.setProduto(produto);
            novoItem.setQuantidade(produtoAdicionadoCarrinhoDTO.quantidade());
            novoItem.setPrecoUnitario(produto.getPreco());
            carrinho.getItens().add(novoItem);
        }
        carrinho.setValorTotal(calcularTotal(carrinho));
        carrinhoRepository.save(carrinho);
    }

    private Optional<ItemCarrinho> buscarItemCarrinho(
            Carrinho carrinho,
            Long produtoId
    ) {
        return carrinho.getItens()
                .stream()
                .filter(item ->
                        item.getProduto().getId().equals(produtoId))
                .findFirst();
    }

    public CarrinhoResponseDTO listarCarrinhoUsuario() {
        Carrinho carrinhoUsuario = obterCarrinhoUsuario();
        return carrinhoMapper.carrinhoToResponseDto(carrinhoUsuario);
    }

    public void alterarQuantidadeItemCarrinho(AtualizarQuantidadeDTO atualizarQuantidadeDTO) {
        if (atualizarQuantidadeDTO.getQuantidade() <= 0) {
            throw new QuantidadeInvalidaException("Quantidade deve ser maior que 0!");
        }
        Carrinho carrinho = obterCarrinhoUsuario();

        Optional<ItemCarrinho> itemExistente =
                buscarItemCarrinho(carrinho, atualizarQuantidadeDTO.getItemId());
        int quantidadeFinal = itemExistente
                .map(i -> i.getQuantidade() + atualizarQuantidadeDTO.getQuantidade())
                .orElse(atualizarQuantidadeDTO.getQuantidade());

        produtoService.validarQuantidadeProduto(atualizarQuantidadeDTO.getItemId(), quantidadeFinal);
        if (itemExistente.isEmpty()) {
            throw new ProdutoNaoEncontradoException(
                    "Produto não encontrado");
        }
        ItemCarrinho item = itemExistente.get();

        item.atualizarQuantidade(atualizarQuantidadeDTO.getQuantidade());

        carrinho.setValorTotal(calcularTotal(carrinho));
        carrinhoRepository.save(carrinho);
    }

    public void removerItem(Long produtoId) {
        Carrinho carrinho = obterCarrinhoUsuario();

        ItemCarrinho item = buscarItemCarrinho(carrinho, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(
                        "Produto não encontrado no carrinho"));

        carrinho.removerItem(item);
        carrinho.setValorTotal(calcularTotal(carrinho));
        carrinhoRepository.save(carrinho);
    }

    public void limparCarrinho() {
        Carrinho carrinho = obterCarrinhoUsuario();

        carrinho.limparCarrinho();
        carrinho.setValorTotal(BigDecimal.ZERO);

        carrinhoRepository.save(carrinho);
    }

    private BigDecimal calcularTotal(Carrinho carrinho) {
        return carrinho.getItens()
                .stream()
                .map(item ->
                        item.getPrecoUnitario()
                                .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
